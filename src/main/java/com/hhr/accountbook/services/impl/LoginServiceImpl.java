package com.hhr.accountbook.services.impl;

import com.hhr.accountbook.dao.AccountDao;
import com.hhr.accountbook.services.LoginService;
import com.hhr.accountbook.model.Account;
import com.hhr.accountbook.util.StringEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Harry
 * @Date: 2021/8/16 18:03
 * @Version 1.0
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private StringEncoderUtil stringEncoderUtil;

    @Override
    public Map<String,Object> login(String accountName, String password){
        Account account = accountDao.findByAccountName(accountName);
        log.info("查询账号结果:" + (account == null ? "null" : account.toString()));
        boolean isLogin = account != null && account.getAccountName().equals(accountName) && account.getPassword().equals(stringEncoderUtil.encode(password));
        Map<String,Object> res = new HashMap<>();
        res.put("isLogin",isLogin);
        res.put("account",account);
        return res;
    }
}
