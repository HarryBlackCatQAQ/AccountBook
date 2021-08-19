package com.hhr.accountbook.view;

import com.gn.lab.ButtonType;
import com.gn.lab.GNButton;
import com.hhr.accountbook.util.RestStageSceneUtil;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Harry
 * @Date: 2021/8/16 4:21
 * @Version 1.0
 */
@Getter
@FXMLView(value = "/views/loginView.fxml")
public class LoginView extends AbstractFxmlView {

    private GNButton loginButton;
    private Pane pane;

    @Autowired
    private RestStageSceneUtil restStageSceneUtil;

    @Override
    public Parent getView() {
        Parent parent = super.getView();
        Pane pane = (Pane) parent;
        initLoginButton(pane);
        this.pane = pane;

        restStageSceneUtil.restStageScene(600,441);

        return parent;
    }

    /**
     *初始化登录按钮
     */
    private void initLoginButton(Pane pane){
        if(loginButton == null){
            loginButton = new GNButton("登录");
            loginButton.setButtonType(ButtonType.CENTRALIZE);
            loginButton.setPrefHeight(34);
            loginButton.setPrefWidth(185);
            loginButton.setLayoutX(206);
            loginButton.setLayoutY(328);
            loginButton.setFont(Font.font(20));

            pane.getChildren().add(loginButton);
        }
    }
}
