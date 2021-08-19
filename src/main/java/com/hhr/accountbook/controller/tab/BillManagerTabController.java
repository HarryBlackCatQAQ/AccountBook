package com.hhr.accountbook.controller.tab;

import com.hhr.accountbook.controller.impl.MainControllerAbstractImpl;
import com.hhr.accountbook.model.Account;
import com.hhr.accountbook.model.PaymentMethod;
import com.hhr.accountbook.model.SpendingType;
import com.hhr.accountbook.services.BillInformationService;
import com.hhr.accountbook.services.BillManagerService;
import com.hhr.accountbook.view.fx.dialog.PromptDialog;
import com.jfoenix.controls.JFXDatePicker;
import de.felixroske.jfxsupport.GUIState;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Harry
 * @Date: 2021/8/19 2:58
 * @Version 1.0
 */

@Slf4j
@Component
public class BillManagerTabController {

    private final static Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");

    @Autowired
    private MainControllerAbstractImpl mainControllerAbstractImpl;

    @Autowired
    private BillInformationService billInformationService;

    @Autowired
    private BillManagerService billManagerService;

    public void initBillManagerTab(){
        //初始化 支付方式 列表
        initPaymentMethodComboBox();
        //初始化 消费类别 列表
        initSpendingTypeComboBox();
        //初始化 日期选择器
        initDatePicker();

//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        mainControllerAbstractImpl.getBillManagerTabPane().getChildren().add(directoryChooser);
    }

    /**
     * 初始化 支付方式 列表
     */
    private void initPaymentMethodComboBox(){
        List<PaymentMethod> paymentMethodList = billInformationService.findAllPaymentMethod();

        for(PaymentMethod paymentMethod : paymentMethodList){
            mainControllerAbstractImpl.getPaymentMethodComboBox2().getItems().add(paymentMethod.getMethod());
        }

        mainControllerAbstractImpl.getPaymentMethodComboBox2().getSelectionModel().selectFirst();
    }

    /**
     * 初始化 消费类别 列表
     */
    private void initSpendingTypeComboBox(){
        List<SpendingType> spendingTypesList = billInformationService.findAllSpendingType();

        for(SpendingType spendingType : spendingTypesList){
            mainControllerAbstractImpl.getSpendingTypeComboBox2().getItems().add(spendingType.getType());
        }

        mainControllerAbstractImpl.getSpendingTypeComboBox2().getSelectionModel().selectFirst();
    }

    /**
     * 初始化 日期选择器
     */
    private void initDatePicker(){
        initDatePicker(mainControllerAbstractImpl.getExportExcelStartTimeDatePicker());
        initDatePicker(mainControllerAbstractImpl.getExportExcelEndTimeDatePicker());
    }
    private void initDatePicker(JFXDatePicker jfxDatePicker){
        jfxDatePicker.setValue(LocalDate.now());
        jfxDatePicker.setShowWeekNumbers(true);
    }



    public void addBillButtonImageClicked() {
        System.err.println("addBillButtonImageClicked");
        String moneyStr = mainControllerAbstractImpl.getMoneyTextField().getText();

        if(moneyStr == null || "".equals(moneyStr) || !isNumeric(moneyStr)){
            PromptDialog promptDialog = new PromptDialog("金额错误或者为空!");
            promptDialog.show();
            return;
        }

        float money = Float.parseFloat(moneyStr);
        String payMethod = mainControllerAbstractImpl.getPaymentMethodComboBox2().getValue();
        String spendingType = mainControllerAbstractImpl.getSpendingTypeComboBox2().getValue();
        String details = mainControllerAbstractImpl.getDetailsTextField().getText();
        Account account = mainControllerAbstractImpl.getAccount();

        billManagerService.addBill(payMethod,spendingType,details,money,account);

        PromptDialog promptDialog = new PromptDialog("添加账单成功!");
        promptDialog.show();
    }


    public void exportExcelButtonClicked() {
        System.err.println("exportExcelButtonClicked");

        if(isExportPathEmpty()){
            PromptDialog promptDialog = new PromptDialog("请选择导出路径!");
            promptDialog.show();
            return;
        }

        billManagerService.exportExcel(mainControllerAbstractImpl.getAccount(),
                mainControllerAbstractImpl.getExportExcelStartTimeDatePicker().getValue(),
                mainControllerAbstractImpl.getExportExcelEndTimeDatePicker().getValue(),
                mainControllerAbstractImpl.getExportPathTextField().getText());
    }


    public void exportExcelToTemplateButtonClicked() {
        System.err.println("exportExcelToTemplateButtonClicked");

        if(isExportPathEmpty()){
            PromptDialog promptDialog = new PromptDialog("请选择导出路径!");
            promptDialog.show();
            return;
        }

        billManagerService.exportExcelToTemplate(
                mainControllerAbstractImpl.getExportPathTextField().getText()
        );
    }

    public void exportPathButtonClicked(){
        DirectoryChooser dirChooser = new DirectoryChooser();
        File file = dirChooser.showDialog(GUIState.getStage());
        if(file == null){
            return;
        }
        mainControllerAbstractImpl.getExportPathTextField().setText(file.getPath());
    }

    /**
     *判断 导出路径是否为空
     */
    private boolean isExportPathEmpty(){
        String path = mainControllerAbstractImpl.getExportPathTextField().getText();
        return path == null || "".equals(path);
    }


    public void importExcelButtonClicked() {
        System.err.println("importExcelButtonClicked");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("xls和xlsx", "*.xls","*.xlsx"),
                new FileChooser.ExtensionFilter("xls", "*.xls"),
                new FileChooser.ExtensionFilter("xlsx", "*.xlsx")
        );
        File file = fileChooser.showOpenDialog(GUIState.getStage());
        if(file == null){
            return;
        }

        billManagerService.importExcel(mainControllerAbstractImpl.getAccount(),file);
    }

    private  boolean isNumeric(String str){
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
