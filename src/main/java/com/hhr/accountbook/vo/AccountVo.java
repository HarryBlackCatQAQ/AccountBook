package com.hhr.accountbook.vo;

import com.hhr.accountbook.model.Account;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Harry
 * @Date: 2021/8/17 16:40
 * @Version 1.0
 */
@Data
public class AccountVo extends RecursiveTreeObject<AccountVo> implements Serializable {
    private StringProperty accountName;
    private StringProperty password;

    private static String inVisible = "************";

    private Account account;

    public AccountVo(String accountName, String password) {
        this.accountName = new SimpleStringProperty(accountName);
        this.password = new SimpleStringProperty(password);
    }

    public AccountVo(Account account){
        this.accountName = new SimpleStringProperty(account.getAccountName());
        this.password = new SimpleStringProperty(inVisible);
        this.account = account;
    }
}
