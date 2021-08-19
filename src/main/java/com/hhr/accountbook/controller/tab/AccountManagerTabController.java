package com.hhr.accountbook.controller.tab;

import com.hhr.accountbook.controller.impl.MainControllerAbstractImpl;
import com.hhr.accountbook.model.Account;
import com.hhr.accountbook.services.AccountManagerService;
import com.hhr.accountbook.view.fx.dialog.*;
import com.hhr.accountbook.vo.AccountVo;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * @Author: Harry
 * @Date: 2021/8/17 20:43
 * @Version 1.0
 */
@Slf4j
@Component
public class AccountManagerTabController {

    @Autowired
    private MainControllerAbstractImpl mainControllerAbstractImpl;

    @Autowired
    private AccountManagerService accountManagerService;

    public void initAccountManagerTab(){
        setupReadOnlyTableView();
    }

    /**
     * 设置 用户管理TreeTableView
     */
    private void setupReadOnlyTableView() {
        List<Account> accountList = accountManagerService.findAllAccount();
        log.info("用户列表:" + accountList);

        setupCellValueFactory(mainControllerAbstractImpl.getAccountNameTreeTableColumn(), AccountVo::getAccountName);
        setupCellValueFactory(mainControllerAbstractImpl.getPasswordTreeTableColumn(), AccountVo::getPassword);

        ObservableList<AccountVo> dummyData = generateDummyData(accountList);
        JFXTreeTableView<AccountVo> treeTableView = mainControllerAbstractImpl.getAccountTreeTableView();
        treeTableView.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));

        treeTableView.setShowRoot(false);
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<AccountVo, T> column, Function<AccountVo, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<AccountVo, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private ObservableList<AccountVo> generateDummyData(List<Account> accountList) {
        ObservableList<AccountVo> dummyData = FXCollections.observableArrayList();

        for(Account account :accountList){
            dummyData.add(new AccountVo(account));
        }
        return dummyData;
    }

    public void editAccountButtonClicked(){
        if(mainControllerAbstractImpl.getAccountTreeTableView().getSelectionModel().getSelectedItem() != null) {
            System.err.println(mainControllerAbstractImpl.getAccountTreeTableView().getSelectionModel().getSelectedItem().getValue());
            Account account = mainControllerAbstractImpl.getAccountTreeTableView().getSelectionModel().getSelectedItem().getValue().getAccount();
            AccountEditDialog accountEditDialog = new AccountEditDialog(account);

            //设置提示窗口按钮和其监听事件
           addAndEditButtonClicked(accountEditDialog,new DialogBuilder.OnClickListener() {
               @Override
               public void onClick() {
                   System.err.println("确定");
                   String accountName = accountEditDialog.getAccountField().getText();
                   String newPassword = accountEditDialog.getPasswordField().getText();
                   String checkPassword = accountEditDialog.getCheckPasswordField().getText();
                   if(newPassword.equals(checkPassword)){
                       accountManagerService.editAccountInfo(account,accountName,newPassword);
                   }
               }
           },"确定");

            accountEditDialog.show();
        }
    }

    public void addAccountButtonClicked(){
        AccountAddDialog accountAddDialog = new AccountAddDialog();

        addAndEditButtonClicked(accountAddDialog, new DialogBuilder.OnClickListener() {
            @Override
            public void onClick() {
                System.err.println("创建");
                String accountName = accountAddDialog.getAccountField().getText();
                String newPassword = accountAddDialog.getPasswordField().getText();
                String checkPassword = accountAddDialog.getCheckPasswordField().getText();
                if(newPassword.equals(checkPassword)){
                    accountManagerService.addAccount(accountName,newPassword);
                    initAccountManagerTab();
                }
            }
        },"创建");

        accountAddDialog.show();
    }

    private void addAndEditButtonClicked(AccountButtonCheckDialog accountButtonCheckDialog,DialogBuilder.OnClickListener onClickListener,String buttonText){
        accountButtonCheckDialog.getDialogBuilder().setPositiveBtn(buttonText,onClickListener);

        accountButtonCheckDialog.getDialogBuilder().getPositiveBtn().setDisable(true);
        textFieldAddListener(accountButtonCheckDialog.getAccountField(),accountButtonCheckDialog);
        textFieldAddListener(accountButtonCheckDialog.getPasswordField(),accountButtonCheckDialog);
        textFieldAddListener(accountButtonCheckDialog.getCheckPasswordField(),accountButtonCheckDialog);
    }

    private void textFieldAddListener(TextField textField,AccountButtonCheckDialog accountButtonCheckDialog){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                accountButtonCheckDialog.isPositiveBtnDisable();
            }
        });
    }


    public void deleteAccountButtonClicked(){
        Account account = mainControllerAbstractImpl.getAccountTreeTableView().getSelectionModel().getSelectedItem().getValue().getAccount();
        if("root".equals(account.getAccountName())){
            return;
        }

        AccountDeleteDialog accountDeleteDialog = new AccountDeleteDialog(account);

        accountDeleteDialog.getDialogBuilder().setPositiveBtn("确定", new DialogBuilder.OnClickListener() {
            @Override
            public void onClick() {
                System.err.println("删除");
                accountManagerService.deleteAccount(account);
                initAccountManagerTab();
            }
        });

        accountDeleteDialog.show();
    }
}
