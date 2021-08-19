package com.hhr.accountbook.services.impl;

import com.hhr.accountbook.controller.impl.MainControllerAbstractImpl;
import com.hhr.accountbook.dao.BillDao;
import com.hhr.accountbook.dao.PaymentMethodDao;
import com.hhr.accountbook.dao.SpendingTypeDao;
import com.hhr.accountbook.dao.impl.QueryDslBillDaoImpl;
import com.hhr.accountbook.exception.PaymentMethodErrorException;
import com.hhr.accountbook.exception.SpendingTypeErrorException;
import com.hhr.accountbook.model.*;
import com.hhr.accountbook.services.BillManagerService;
import com.hhr.accountbook.util.DateUtil;
import com.hhr.accountbook.util.ExcelUtil;
import com.hhr.accountbook.util.ResourcesPathUtil;
import com.hhr.accountbook.util.SnowFlakeUtil;
import com.hhr.accountbook.view.fx.dialog.PromptDialog;
import com.hhr.accountbook.vo.BillVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author: Harry
 * @Date: 2021/8/19 3:14
 * @Version 1.0
 */
@Service
@Slf4j
@Transactional
public class BillManagerServiceImpl implements BillManagerService {

    private static List<ExcelBean> excel;
    private static Map<Integer, List<ExcelBean>> map;

    static{
        excel = new ArrayList<>();
        map = new LinkedHashMap<>();

        excel.add(new ExcelBean("编号", "id", 0));
        excel.add(new ExcelBean("详情", "details", 0));
        excel.add(new ExcelBean("支付方式", "payMethod", 0));
        excel.add(new ExcelBean("类别", "spendingType", 0));
        excel.add(new ExcelBean("金额", "money", 0));
        excel.add(new ExcelBean("时间", "payTime", 0));

        map.put(0,excel);
    }

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private SpendingTypeDao spendingTypeDao;

    @Autowired
    private BillDao billDao;

    @Autowired
    private QueryDslBillDaoImpl queryDslBillDao;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Autowired
    private ExcelUtil excelUtil;

    @Override
    public void addBill(String payMethod, String spendingTypeStr, String details, Float money, Account account) {
        PaymentMethod paymentMethod = paymentMethodDao.getPaymentMethodByMethod(payMethod);
        SpendingType spendingType = spendingTypeDao.getSpendingTypeByType(spendingTypeStr);

        Bill bill = new Bill();
        bill.setPaymentMethod(paymentMethod.getId());
        bill.setSpendingType(spendingType.getId());
        bill.setDetails(details);
        bill.setMoney(money);
        bill.setAccountId(account.getId());
        bill.setPayTime(dateUtil.now());
        bill.setId(String.valueOf(snowFlakeUtil.nextId()));

        billDao.saveAndFlush(bill);
    }

    @Override
    public void exportExcel(Account account, LocalDate startTime, LocalDate endTime,String path) {
        List<BillVo> billVoList = queryDslBillDao.findBillVoByTime(account.getId(),
                dateUtil.localDateToStartTimestamp(startTime),
                dateUtil.localDateToEndTimestamp(endTime));

        Map<String,List<BillVo>> map = new LinkedHashMap<>();
        Map<String,Map<String,List<BillVo>>> yearMap = new LinkedHashMap<>();
        for(BillVo billVo : billVoList){

            Timestamp ts = Timestamp.valueOf(dateUtil.fixedTime(billVo.getPayTime()));
            int year = ts.getYear() + 1900;
            int month = ts.getMonth() + 1;
            String key = year + "-" + month;

            String yearStr = String.valueOf(year);
            String monthStr = String.valueOf(month);


            billVo.setPayTime(dateUtil.fixedTimeForExportExcel(billVo.getPayTime()));

            if(!map.containsKey(key)){
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(billVo);

            /*
            月份也分文件夹
             */
//            if(!yearMap.containsKey(yearStr)){
//                yearMap.put(yearStr,new LinkedHashMap<>());
//            }
//
//            Map<String,List<BillVo>> monthMap = yearMap.get(yearStr);
//
//            if(!monthMap.containsKey(monthStr)){
//                monthMap.put(monthStr,new ArrayList<>());
//            }
//
//            monthMap.get(monthStr).add(billVo);
        }

        char c = File.separatorChar;
        String createTime = dateUtil.nowForMat();
        try {
            for (Map.Entry<String, List<BillVo>> entry : map.entrySet()) {
                File file = new File(path + c + "记账本Excel" + createTime + c + entry.getKey().substring(0, 4));
                //如果文件夹不存在
                if (!file.exists()) {
                    //创建文件夹
                    file.mkdirs();
                }

                String fileName = entry.getKey() + ".xlsx";
                exportExcel(entry.getValue(), entry.getKey(), file.getPath() + c + fileName);
            }

            /*
            月份也分文件夹
             */
//            for(Map.Entry<String,Map<String,List<BillVo>>> yearMapEntry : yearMap.entrySet()){
//                for(Map.Entry<String,List<BillVo>> monthMapEntry : yearMapEntry.getValue().entrySet()){
//                    File file = new File(path + c + "记账本Excel" + dateUtil.nowForMat() + c + yearMapEntry.getKey() + c + monthMapEntry.getKey());
//
//                    if (!file.exists()) {
//                        //创建文件夹
//                        file.mkdirs();
//                    }
//
//                    String fileName = yearMapEntry.getKey() + "-" + monthMapEntry.getKey();
//                    String fileFullName = fileName + ".xlsx";
//                    exportExcel(monthMapEntry.getValue(), fileName, file.getPath() + c + fileFullName);
//                }
//            }

            PromptDialog promptDialog = new PromptDialog("导出Excel成功!");
            promptDialog.show();
        }catch (Exception e){
            log.error("生成Excel错误!" + e);
            PromptDialog promptDialog = new PromptDialog("生成Excel失败!");
            promptDialog.show();
        }
    }

    @Autowired
    private MainControllerAbstractImpl mainControllerAbstractImpl;

    @Override
    public void exportExcelToTemplate(String path) {
        char c = File.separatorChar;
        try {
//            System.err.println(ResourcesPathUtil.getPathOfUrl("/template/template.xls").getPath());
//            System.out.println(System.getProperty("user.dir") + c + "template" + c + "template.xls");
//            System.err.println(ResourcesPathUtil.getPathOfUrl("/"));
//            mainControllerAbstractImpl.getDetailsTextField().setText(System.getProperty("user.dir"));
            File file3 = new File(System.getProperty("user.dir") + c + "template" + c + "template.xls");
            File dest = new File(path + c + "模板.xls");
            copyFileUsingFileStreams(file3,dest);

            PromptDialog promptDialog = new PromptDialog("生成Excel模板成功!");
            promptDialog.show();
        }catch (Exception e){
            log.error("生成Excel模板错误!" + e);
            PromptDialog promptDialog = new PromptDialog("生成Excel模板失败!");
            promptDialog.show();
        }
    }

    private  void copyFileUsingFileStreams(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;

        input = new FileInputStream(source);
        output = new FileOutputStream(dest);

        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buf)) > 0) {
            output.write(buf, 0, bytesRead);
        }
        input.close();
        output.close();
    }

    private void exportExcel(List<BillVo> billVoList,String sheetName,String path) throws Exception{
        XSSFWorkbook workbook = excelUtil.createExcelFile(BillVo.class, billVoList, map, sheetName);
        FileOutputStream out = new FileOutputStream(path);
        workbook.write(out);
        out.close();
    }

    @Override
    public void importExcel(Account account, File file) {
        List<Bill> billList = new ArrayList<>();

        try {
            InputStream inputStream = new FileInputStream(file);
            try {
                List<List<Object>> list = excelUtil.getUserListByExcel(inputStream,file.getName());

                inputStream.close();

                List<PaymentMethod> paymentMethodList = paymentMethodDao.findAll();
                List<SpendingType> spendingTypeList = spendingTypeDao.findAll();

                for(List<Object> objectList : list){
                    String id = objectList.get(0).toString();
                    String details = objectList.get(1).toString();
                    String payMethod = objectList.get(2).toString();
                    String spendingType = objectList.get(3).toString();
                    Float money = Float.parseFloat(objectList.get(4).toString());
                    String time = objectList.get(5).toString();

                    if("null".equals(id)){
                        id = String.valueOf(snowFlakeUtil.nextId());
                    }

                    Long payMethodId = findPaymentMethodId(paymentMethodList,payMethod);
                    Long spendingTypeId = findSpendingTypeId(spendingTypeList,spendingType);

                    Date date = DateUtil.sdf2.parse(time);
                    String time2 = DateUtil.sdf.format(date);

                    Bill bill = new Bill();
                    bill.setId(id);
                    bill.setDetails(details);
                    bill.setPayTime(time2);
                    bill.setMoney(money);
                    bill.setAccountId(account.getId());
                    bill.setSpendingType(spendingTypeId);
                    bill.setPaymentMethod(payMethodId);

                    billList.add(bill);
                }

            } catch (Exception e) {
                log.error("转换失败:" + e);

                PromptDialog promptDialog = new PromptDialog("Excel格式错误,请按照模板仔细检查!");
                promptDialog.show();
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream关闭! " + e);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("打开文件失败:" + e);

            PromptDialog promptDialog = new PromptDialog("打开Excel错误,请按照模板仔细检查!");
            promptDialog.show();
        }

        if(billList.size() != 0){
            billDao.saveAllAndFlush(billList);
            PromptDialog promptDialog = new PromptDialog("导入Excel成功!");
            promptDialog.show();
        }


    }

    private Long findPaymentMethodId(List<PaymentMethod> paymentMethodList,String payMethod) throws Exception{
        for(PaymentMethod paymentMethod : paymentMethodList){
            if(paymentMethod.getMethod().equals(payMethod)){
                return paymentMethod.getId();
            }
        }

        throw new PaymentMethodErrorException("未找到库中的支付方式!");
    }
    private Long findSpendingTypeId(List<SpendingType> spendingTypeList,String spendingType) throws Exception{
        for(SpendingType spendingType1 : spendingTypeList){
            if(spendingType1.getType().equals(spendingType)){
                return spendingType1.getId();
            }
        }

        throw new SpendingTypeErrorException("未找到库中的消费类型!");
    }
}
