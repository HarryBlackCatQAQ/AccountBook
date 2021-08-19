package com.hhr.accountbook.dao;

import com.hhr.accountbook.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Harry
 * @Date: 2021/8/16 18:08
 * @Version 1.0
 */
public interface AccountDao extends JpaRepository<Account, Long> {
    public Account findByAccountName(String accountName);
}
