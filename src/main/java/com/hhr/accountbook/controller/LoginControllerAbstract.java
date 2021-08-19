package com.hhr.accountbook.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.Getter;

/**
 * @Author: Harry
 * @Date: 2021/8/16 4:16
 * @Version 1.0
 */
@Getter
public abstract class LoginControllerAbstract implements Initializable {
    @FXML
    private ImageView loginLogoImageView;

    @FXML
    private VBox centerVBox;

    @FXML
    private JFXTextField userTextField;

    @FXML
    private JFXPasswordField passwordField;
}
