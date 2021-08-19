package com.hhr.accountbook.info;

import com.hhr.accountbook.model.Account;
import com.hhr.accountbook.view.fx.dialog.LoadingDialog;
import lombok.Data;


/**
 * @Author: Harry
 * @Date: 2021/8/16 20:58
 * @Version 1.0
 */
@Data
public class LoginAccountInfo {
    private static LoginAccountInfo instance;

    private Account account;

    /**
     * 单例模式
     * @return
     */
    public static LoginAccountInfo getInstance() {
        if (instance == null) {
            synchronized (LoginAccountInfo.class) {
                if (instance == null) {
                    instance = new LoginAccountInfo();
                }
            }
        }
        return instance;
    }

    private LoginAccountInfo(){

    }
}
