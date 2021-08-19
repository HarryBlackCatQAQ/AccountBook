package com.hhr.accountbook.services;

import com.hhr.accountbook.model.Account;

import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/18 3:54
 * @Version 1.0
 */
public interface AccountManagerService {

    public List<Account> findAllAccount();

    public void editAccountInfo(Account account,String accountName,String newPassword);

    public void addAccount(String accountName,String newPassword);

    public void deleteAccount(Account account);
}
