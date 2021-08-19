package com.hhr.accountbook.services;

import java.util.Map;

/**
 * @Author: Harry
 * @Date: 2021/8/16 18:03
 * @Version 1.0
 */
public interface LoginService {
    public Map<String,Object> login(String accountName, String password);
}
