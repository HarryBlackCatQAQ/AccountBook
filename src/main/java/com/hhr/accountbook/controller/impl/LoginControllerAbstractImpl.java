package com.hhr.accountbook.controller.impl;

import com.hhr.accountbook.AccountBookApplication;
import com.hhr.accountbook.controller.LoginControllerAbstract;
import com.hhr.accountbook.info.LoginAccountInfo;
import com.hhr.accountbook.model.Account;
import com.hhr.accountbook.services.LoginService;
import com.hhr.accountbook.view.LoginView;
import com.hhr.accountbook.view.MainView;
import com.hhr.accountbook.view.fx.dialog.LoadingDialog;
import com.hhr.accountbook.view.fx.dialog.PromptDialog;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author: Harry
 * @Date: 2021/8/16 4:17
 * @Version 1.0
 */
@FXMLController
public class LoginControllerAbstractImpl extends LoginControllerAbstract {

    @Autowired
    private LoginView loginView;

    @Autowired
    private LoginService loginService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initEvent();
    }

    private void loginButtonClicked(){
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.show();
        String accountName = this.getUserTextField().getText();
        String password = this.getPasswordField().getText();
        Map<String,Object> res = loginService.login(accountName,password);
        boolean isLogin = (boolean)res.get("isLogin");
        Account account = (Account)res.get("account");
        loadingDialog.close();
        if(!isLogin){
            PromptDialog promptDialog = new PromptDialog("用户名或者密码不正确!");
            promptDialog.show();
        }
        else{
            LoginAccountInfo.getInstance().setAccount(account);
            AccountBookApplication.showView(MainView.class);
        }
    }

    private void initEventImpl(){
        //初始化点击按钮事件
        loginView.getLoginButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButtonClicked();
            }
        });

        //初始化Enter登录事件
        loginView.getPane().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().getName().equals(KeyCode.ENTER.getName())){
                    loginButtonClicked();
                }
            }
        });
    }

    private void initEvent(){
        Platform.runLater(this::initEventImpl);
    }
}
