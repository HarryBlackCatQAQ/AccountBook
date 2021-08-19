package com.hhr.accountbook.services.impl;

import com.hhr.accountbook.dao.BillDao;
import com.hhr.accountbook.dao.PaymentMethodDao;
import com.hhr.accountbook.dao.SpendingTypeDao;
import com.hhr.accountbook.dao.impl.QueryDslBillDaoImpl;
import com.hhr.accountbook.model.Account;
import com.hhr.accountbook.model.PaymentMethod;
import com.hhr.accountbook.model.SpendingType;
import com.hhr.accountbook.services.BillInformationService;
import com.hhr.accountbook.util.DateUtil;
import com.hhr.accountbook.vo.BillVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/18 5:45
 * @Version 1.0
 */
@Service
@Slf4j
@Transactional
public class BillInformationServiceImpl implements BillInformationService {

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private SpendingTypeDao spendingTypeDao;

    @Autowired
    private QueryDslBillDaoImpl queryDslBillDao;

    @Autowired
    private BillDao billDao;

    @Autowired
    private DateUtil dateUtil;

    @Override
    public List<PaymentMethod> findAllPaymentMethod() {
        List<PaymentMethod> paymentMethodList = paymentMethodDao.findAll();
        log.info("全部支付方式:" + paymentMethodList);
        return paymentMethodList;
    }

    @Override
    public List<SpendingType> findAllSpendingType() {
        List<SpendingType> spendingTypeList = spendingTypeDao.findAll();
        log.info("全部类别:" + spendingTypeList);

        return spendingTypeList;
    }

    @Override
    public List<BillVo> findBillVoByChoose(Account account, LocalDate startTime, LocalDate endTime,String paymentMethod,String spendingType) {

        List<BillVo> billVoList  = queryDslBillDao.findAllBillVoByChoose(
                account.getId(),
                dateUtil.localDateToStartTimestamp(startTime),
                dateUtil.localDateToEndTimestamp(endTime),
                paymentMethod, spendingType);

        log.info(billVoList.toString());

        for(BillVo billVo : billVoList){
            billVo.setPayTime(dateUtil.fixedTime(billVo.getPayTime()));
        }

        return billVoList;
    }

    @Modifying
    @Override
    public void deleteBill(BillVo billVo) {
        billDao.deleteById(billVo.getId());
    }
}
