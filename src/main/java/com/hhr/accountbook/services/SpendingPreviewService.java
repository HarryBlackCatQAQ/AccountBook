package com.hhr.accountbook.services;

import com.hhr.accountbook.model.Account;
import com.hhr.accountbook.vo.BillSpendingTypeVo;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/18 4:18
 * @Version 1.0
 */
public interface SpendingPreviewService {

    public List<BillSpendingTypeVo> findAccountSpendTypeByTime(Account account, LocalDate startTime, LocalDate endTime);

//    public List<BillVo> findAccountBillVoByTime(Account account,LocalDate startTime,LocalDate endTime);

    public Double findAccountSumMoneyByTime(Account account,LocalDate startTime,LocalDate endTime);
}
