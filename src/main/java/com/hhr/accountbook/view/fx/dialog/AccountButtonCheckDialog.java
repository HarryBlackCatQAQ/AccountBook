package com.hhr.accountbook.view.fx.dialog;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Harry
 * @Date: 2021/8/18 0:14
 * @Version 1.0
 */
@Getter
@Setter
public abstract class AccountButtonCheckDialog extends MainDialog{
    private JFXTextField accountField;
    private JFXPasswordField passwordField;
    private JFXPasswordField checkPasswordField;

    public void isPositiveBtnDisable(){

    }
}
