package com.hhr.accountbook.services;

import com.hhr.accountbook.model.Account;
import com.hhr.accountbook.model.PaymentMethod;
import com.hhr.accountbook.model.SpendingType;
import com.hhr.accountbook.vo.BillVo;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/18 5:45
 * @Version 1.0
 */
public interface BillInformationService {
    public List<PaymentMethod> findAllPaymentMethod();

    public List<SpendingType> findAllSpendingType();

    public List<BillVo> findBillVoByChoose(Account account, LocalDate startTime, LocalDate endTime, String paymentMethod, String spendingType);

    public void deleteBill(BillVo billVo);
}
