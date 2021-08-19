package com.hhr.accountbook.view.fx.dialog;

import com.hhr.accountbook.model.Account;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.felixroske.jfxsupport.GUIState;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import lombok.Getter;

/**
 * @Author: Harry
 * @Date: 2021/8/17 21:11
 * @Version 1.0
 */
@Getter
public class AccountEditDialog extends AccountButtonCheckDialog{

    public AccountEditDialog(Account account){
        Window window = GUIState.getStage().getScene().getWindow();
        dialogBuilder = new DialogBuilder(window);

        VBox vBox = dialogBuilder.getLayoutContentVBox();
        vBox.setSpacing(25);

        HBox accountHBox = new HBox();
        accountHBox.setSpacing(25);
        Label accountLabel = new Label("账      号:");
        this.setAccountField(new JFXTextField());
        this.getAccountField().setPrefWidth(200);
        accountHBox.getChildren().addAll(accountLabel,this.getAccountField());

        HBox passwordHBox = new HBox();
        passwordHBox.setSpacing(25);
        Label passwordLabel = new Label("密      码:");
        this.setPasswordField(new JFXPasswordField());
        this.getPasswordField().setPrefWidth(200);
        passwordHBox.getChildren().addAll(passwordLabel, this.getPasswordField());


        HBox checkPasswordHBox = new HBox();
        checkPasswordHBox.setSpacing(25);
        Label checkPasswordLabel = new Label("重复密码:");
        this.setCheckPasswordField(new JFXPasswordField());
        this.getCheckPasswordField().setPrefWidth(200);
        checkPasswordHBox.getChildren().addAll(checkPasswordLabel,this.getCheckPasswordField());

        vBox.getChildren().addAll(accountHBox,passwordHBox,checkPasswordHBox);


        dialogBuilder.setNegativeBtn("取消", new DialogBuilder.OnClickListener() {
            @Override
            public void onClick() {
            }
        });


        this.getAccountField().setText(account.getAccountName());
        if("root".equals(account.getAccountName())){
            this.getAccountField().setDisable(true);
        }
        this.getPasswordField().setText(account.getPassword());
    }

    @Override
    public void isPositiveBtnDisable(){
        String accountName = this.getAccountField().getText();
        String password = this.getPasswordField().getText();
        String checkPassword = this.getCheckPasswordField().getText();

        dialogBuilder.getPositiveBtn().setDisable(!password.equals(checkPassword));
    }
}
