package com.hhr.accountbook.controller.impl;

import com.hhr.accountbook.AccountBookApplication;
import com.hhr.accountbook.controller.MainControllerAbstract;
import com.hhr.accountbook.controller.tab.AccountManagerTabController;
import com.hhr.accountbook.controller.tab.BillInformationTabController;
import com.hhr.accountbook.controller.tab.BillManagerTabController;
import com.hhr.accountbook.controller.tab.SpendingPreviewTabController;
import com.hhr.accountbook.info.LoginAccountInfo;
import com.hhr.accountbook.model.Account;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @Author: Harry
 * @Date: 2021/8/16 21:16
 * @Version 1.0
 */
@Slf4j
@FXMLController
public class MainControllerAbstractImpl extends MainControllerAbstract {
    private Account account;

    @Autowired
    private SpendingPreviewTabController spendingPreviewTabController;

    @Autowired
    private AccountManagerTabController accountManagerTabController;

    @Autowired
    private BillInformationTabController billInformationTabController;

    @Autowired
    private BillManagerTabController billManagerTabController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMainView();
    }

    @Override
    public void initMainView(){
        account = LoginAccountInfo.getInstance().getAccount();
        this.getAccountNameLabel().setText("  " + account.getAccountName() + ",您好!");
        log.info("用户:" + account.getAccountName() + ",登录!");

        //初始化消费预览tab
        Platform.runLater(spendingPreviewTabController::initSpendingPreviewTab);

        //初始化账单详情tab
        Platform.runLater(billInformationTabController::initBillInformationTab);

        //初始化账单管理tab
        Platform.runLater(billManagerTabController::initBillManagerTab);

        //初始化用户管理tab
        boolean isRootAccount = "root".equals(account.getAccountName());
        log.info("开启root用户权限:" + isRootAccount);
        openRootAccountTab(isRootAccount);
        if(isRootAccount){
            accountManagerTabController.initAccountManagerTab();
        }

    }

    /**
     * BillInformationTab Event
     */
    @FXML
    private void refreshButtonClicked(MouseEvent event) {
        billInformationTabController.refreshButtonClicked();
    }

    @FXML
    private void deleteBillButtonClicked(MouseEvent event) {
        billInformationTabController.deleteBillButtonClicked();
    }


    /**
     * BillManagerTab Event
     */
    @FXML
    private void addBillButtonImageClicked(MouseEvent event) {
        billManagerTabController.addBillButtonImageClicked();
    }

    @FXML
    private void exportExcelButtonClicked(MouseEvent event) {
        billManagerTabController.exportExcelButtonClicked();
    }

    @FXML
    private void exportExcelToTemplateButtonClicked(MouseEvent event) {
        billManagerTabController.exportExcelToTemplateButtonClicked();
    }

    @FXML
    private void exportPathButtonClicked(MouseEvent event) {
        billManagerTabController.exportPathButtonClicked();
    }


    @FXML
    private void importExcelButtonClicked(MouseEvent event) {
        billManagerTabController.importExcelButtonClicked();
    }


    /**
     * AccountManagerTab Event
     */
    @FXML
    private void addAccountButtonClicked(MouseEvent event) {
//        System.err.println("addAccountButtonClicked");
        accountManagerTabController.addAccountButtonClicked();
    }

    @FXML
    private void editAccountButtonClicked(MouseEvent event) {
        accountManagerTabController.editAccountButtonClicked();
    }

    @FXML
    private void deleteAccountButtonClicked(MouseEvent event) {
//        System.err.println("deleteAccountButtonClicked");
        accountManagerTabController.deleteAccountButtonClicked();
    }


    @Autowired
    private AccountBookApplication accountBookApplication;
    @FXML
    private void logoutImageButtonClicked(MouseEvent event) {
        log.info(account.getAccountName() + ",退出登录!");
        LoginAccountInfo.getInstance().setAccount(null);
        accountBookApplication.relaunch();
    }

    /**
     * 是否开启root用户tab
     * @param isRootAccount
     */
    private void openRootAccountTab(boolean isRootAccount){
        this.getAccountManagerTab().setDisable(!isRootAccount);
    }


    public Account getAccount() {
        return account;
    }
}
