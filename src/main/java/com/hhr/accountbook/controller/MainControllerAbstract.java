package com.hhr.accountbook.controller;

import com.hhr.accountbook.vo.AccountVo;
import com.hhr.accountbook.vo.BillTreeObjectVo;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

/**
 * @Author: Harry
 * @Date: 2021/8/16 21:16
 * @Version 1.0
 */
@Getter
public abstract class MainControllerAbstract implements Initializable {

    @FXML
    private JFXTabPane mainViewTabPane;

    @FXML
    private Label accountNameLabel;

    @FXML
    private ImageView logoutImageButton;


    /**
     * 消费预览Tab
     */
    @FXML
    private AnchorPane anchorPaneOne;

    @FXML
    private JFXDatePicker endDatePicker;

    @FXML
    private JFXDatePicker startDatePicker;

    @FXML
    private PieChart dataPieChart;

    @FXML
    private Label sumMoneyLabel;

    /**
     * 账单详情Tab
     */
    @FXML
    private JFXTreeTableView<BillTreeObjectVo> billTreeTableView;

    @FXML
    private JFXTreeTableColumn<BillTreeObjectVo, String> idTreeTableColumn;

    @FXML
    private JFXTreeTableColumn<BillTreeObjectVo, String> detailsTreeTableColumn;

    @FXML
    private JFXTreeTableColumn<BillTreeObjectVo, String> paymentMethodTreeTableColumn;

    @FXML
    private JFXTreeTableColumn<BillTreeObjectVo, String> spendingTypeTreeTableColumn;

    @FXML
    private JFXTreeTableColumn<BillTreeObjectVo, String> moneyTreeTableColumn;

    @FXML
    private JFXTreeTableColumn<BillTreeObjectVo, String> paytimeTreeTableColumn;

    @FXML
    private JFXComboBox<String> paymentMethodComboBox;

    @FXML
    private JFXComboBox<String>  spendingTypeComboBox;

    @FXML
    private JFXDatePicker startTimeDatePicker;

    @FXML
    private JFXDatePicker endTimeDatePicker;

    @FXML
    private ImageView refreshButtonImageView;

    @FXML
    private ImageView deleteBillButtonImageView;


    /**
     * 账单管理Tab
     */
    @FXML
    private AnchorPane billManagerTabPane;

    @FXML
    private JFXComboBox<String> paymentMethodComboBox2;

    @FXML
    private JFXComboBox<String> spendingTypeComboBox2;

    @FXML
    private JFXTextField detailsTextField;

    @FXML
    private JFXTextField moneyTextField;

    @FXML
    private ImageView addBillButtonImageView;

    @FXML
    private JFXDatePicker exportExcelStartTimeDatePicker;

    @FXML
    private JFXDatePicker exportExcelEndTimeDatePicker;

    @FXML
    private ImageView exportExcelButtonImageView;

    @FXML
    private ImageView importExcelButtonImageView;

    @FXML
    private ImageView exportExcelToTemplateButtonImageView;

    @FXML
    private ImageView exportPathButtonImageView;

    @FXML
    private JFXTextField exportPathTextField;


    /**
     * 用户管理Tab
     */
    @FXML
    private Tab accountManagerTab;

    @FXML
    private JFXTreeTableView<AccountVo> accountTreeTableView;

    @FXML
    private JFXTreeTableColumn<AccountVo, String> accountNameTreeTableColumn;

    @FXML
    private JFXTreeTableColumn<AccountVo, String> passwordTreeTableColumn;

    @FXML
    private ImageView editAccountButtonImageView;

    @FXML
    private ImageView addAccountButtonImageView;

    @FXML
    private ImageView deleteAccountButtonImageView;



    public void initMainView(){}
}
