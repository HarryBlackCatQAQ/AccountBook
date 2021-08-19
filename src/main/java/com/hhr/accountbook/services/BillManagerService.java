package com.hhr.accountbook.services;

import com.hhr.accountbook.model.Account;

import java.io.File;
import java.time.LocalDate;

/**
 * @Author: Harry
 * @Date: 2021/8/19 3:14
 * @Version 1.0
 */
public interface BillManagerService {

    public void addBill(String payMethod, String spendingType, String details,Float money, Account account);

    public void exportExcel(Account account, LocalDate startTime,LocalDate endTime,String path);

    public void exportExcelToTemplate(String path);

    public void importExcel(Account account, File file);
}
