package com.hhr.accountbook.services.impl;

import com.hhr.accountbook.dao.BillDao;
import com.hhr.accountbook.model.Account;
import com.hhr.accountbook.services.SpendingPreviewService;
import com.hhr.accountbook.util.DateUtil;
import com.hhr.accountbook.util.EntityUtil;
import com.hhr.accountbook.vo.BillSpendingTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/18 4:18
 * @Version 1.0
 */
@Slf4j
@Service
public class SpendingPreviewServiceImpl implements SpendingPreviewService {

    @Autowired
    private BillDao billDao;

    @Autowired
    private EntityUtil entityUtil;

    @Autowired
    private DateUtil dateUtil;

    @Override
    public List<BillSpendingTypeVo> findAccountSpendTypeByTime(Account account, LocalDate startTime, LocalDate endTime) {
        List<Object[]> res = billDao.findAllBillSpendingTypeVoByAccountId(
                account.getId(),
                dateUtil.localDateToString(startTime),
                dateUtil.localDateToString(endTime) + " 23:59:59");
        List<BillSpendingTypeVo> billSpendingTypeVoList = entityUtil.castEntity(res,BillSpendingTypeVo.class,new BillSpendingTypeVo());
        log.info(billSpendingTypeVoList.toString());

        return billSpendingTypeVoList;
    }

//    @Override
//    public List<BillVo> findAccountBillVoByTime(Account account, LocalDate startTime, LocalDate endTime) {
//        List<Object[]> res = billDao.findAllBillVoByAccountId(
//                account.getId(),
//                localDateToString(startTime),
//                localDateToString(endTime) + " 23:59:59");
//        List<BillVo> billVoList = entityUtil.castEntity(res, BillVo.class,new BillVo());
//        System.err.println(billVoList);
//        log.info(billVoList.toString());
//
//        return billVoList;
//    }

    @Override
    public Double findAccountSumMoneyByTime(Account account, LocalDate startTime, LocalDate endTime) {
        return billDao.findSumMoneyByAccountIdAndDate(
                account.getId(),
                dateUtil.localDateToString(startTime),
                dateUtil.localDateToString(endTime) + " 23:59:59");
    }

}
