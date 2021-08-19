package com.hhr.accountbook.services.impl;

import com.hhr.accountbook.dao.AccountDao;
import com.hhr.accountbook.dao.BillDao;
import com.hhr.accountbook.model.Account;
import com.hhr.accountbook.services.AccountManagerService;
import com.hhr.accountbook.util.StringEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/18 3:55
 * @Version 1.0
 */
@Slf4j
@Service
public class AccountManagerServiceImpl implements AccountManagerService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private BillDao billDao;

    @Autowired
    private StringEncoderUtil stringEncoderUtil;

    @Override
    public List<Account> findAllAccount() {
        return accountDao.findAll();
    }

    @Override
    public void editAccountInfo(Account account,String accountName,String newPassword) {
        account.setAccountName(accountName);
        account.setPassword(stringEncoderUtil.encode(newPassword));
        accountDao.save(account);
        log.info("更新后的账号:" + account);
    }

    @Override
    public void addAccount(String accountName,String newPassword) {
        Account account = new Account();
        account.setAccountName(accountName);
        account.setPassword(stringEncoderUtil.encode(newPassword));
        accountDao.save(account);
        log.info("新添加的账号:" + account);
    }

    @Override
    public void deleteAccount(Account account) {
        Long accountId = account.getId();
        accountDao.delete(account);
        System.err.println(account);
        billDao.deleteBillByAccountId2(accountId);
        log.info("删除的账号:" + account);
    }
}
