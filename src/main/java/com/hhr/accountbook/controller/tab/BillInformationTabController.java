package com.hhr.accountbook.controller.tab;

import com.hhr.accountbook.controller.impl.MainControllerAbstractImpl;
import com.hhr.accountbook.model.PaymentMethod;
import com.hhr.accountbook.model.SpendingType;
import com.hhr.accountbook.services.BillInformationService;
import com.hhr.accountbook.view.fx.dialog.BillDeleteDialog;
import com.hhr.accountbook.view.fx.dialog.DialogBuilder;
import com.hhr.accountbook.vo.BillTreeObjectVo;
import com.hhr.accountbook.vo.BillVo;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeTableColumn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

/**
 * @Author: Harry
 * @Date: 2021/8/18 5:42
 * @Version 1.0
 */
@Slf4j
@Component
public class BillInformationTabController {

    @Autowired
    private MainControllerAbstractImpl mainControllerAbstractImpl;

    @Autowired
    private BillInformationService billInformationService;

    public void initBillInformationTab(){
        //初始化 支付方式 列表
        initPaymentMethodComboBox();
        //初始化 消费类别 列表
        initSpendingTypeComboBox();
        //初始化 日期选择器
        initDatePicker();

        //初始化账单列表
        setupBillTreeTableView();
    }

    /**
     * 初始化 支付方式 列表
     */
    private void initPaymentMethodComboBox(){
        List<PaymentMethod> paymentMethodList = billInformationService.findAllPaymentMethod();

        mainControllerAbstractImpl.getPaymentMethodComboBox().getItems().add("全部");
        for(PaymentMethod paymentMethod : paymentMethodList){
            mainControllerAbstractImpl.getPaymentMethodComboBox().getItems().add(paymentMethod.getMethod());
        }

        mainControllerAbstractImpl.getPaymentMethodComboBox().getSelectionModel().select("全部");

        addComboBoxListener(mainControllerAbstractImpl.getPaymentMethodComboBox());
    }

    /**
     * 初始化 消费类别 列表
     */
    private void initSpendingTypeComboBox(){
        List<SpendingType> spendingTypesList = billInformationService.findAllSpendingType();

        mainControllerAbstractImpl.getSpendingTypeComboBox().getItems().add("全部");
        for(SpendingType spendingType : spendingTypesList){
            mainControllerAbstractImpl.getSpendingTypeComboBox().getItems().add(spendingType.getType());
        }

        mainControllerAbstractImpl.getSpendingTypeComboBox().getSelectionModel().select("全部");

        addComboBoxListener(mainControllerAbstractImpl.getSpendingTypeComboBox());
    }


    private void addComboBoxListener(ComboBox<String> comboBox){
        comboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                System.err.println(newValue);
                setupBillTreeTableView();
            }
        });
    }

    /**
     * 初始化 日期选择器
     */
    private void initDatePicker(){
        initDatePicker(mainControllerAbstractImpl.getStartTimeDatePicker());
        initDatePicker(mainControllerAbstractImpl.getEndTimeDatePicker());
    }
    private void initDatePicker(JFXDatePicker jfxDatePicker){
        jfxDatePicker.setValue(LocalDate.now());
        jfxDatePicker.setShowWeekNumbers(true);
        jfxDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                setupBillTreeTableView();
            }
        });
    }



    private void setupBillTreeTableView() {
        if(mainControllerAbstractImpl.getStartTimeDatePicker().getValue().compareTo(mainControllerAbstractImpl.getEndTimeDatePicker().getValue()) > 0){
            return;
        }

        List<BillVo> billVoList = billInformationService.findBillVoByChoose(
                mainControllerAbstractImpl.getAccount(),
                mainControllerAbstractImpl.getStartTimeDatePicker().getValue(),
                mainControllerAbstractImpl.getEndTimeDatePicker().getValue(),
                mainControllerAbstractImpl.getPaymentMethodComboBox().getSelectionModel().getSelectedItem(),
                mainControllerAbstractImpl.getSpendingTypeComboBox().getSelectionModel().getSelectedItem()
        );

        setupCellValueFactory(mainControllerAbstractImpl.getIdTreeTableColumn(),BillTreeObjectVo::getId);
        setupCellValueFactory(mainControllerAbstractImpl.getDetailsTreeTableColumn(), BillTreeObjectVo::getDetails);
        setupCellValueFactory(mainControllerAbstractImpl.getPaymentMethodTreeTableColumn(), BillTreeObjectVo::getPayMethod);
        setupCellValueFactory(mainControllerAbstractImpl.getSpendingTypeTreeTableColumn(), BillTreeObjectVo::getSpendingType);
        setupCellValueFactory(mainControllerAbstractImpl.getMoneyTreeTableColumn(), BillTreeObjectVo::getMoney);
        setupCellValueFactory(mainControllerAbstractImpl.getPaytimeTreeTableColumn(), BillTreeObjectVo::getPayTime);

        ObservableList<BillTreeObjectVo> dummyData = generateDummyData(billVoList);
        JFXTreeTableView<BillTreeObjectVo> treeTableView = mainControllerAbstractImpl.getBillTreeTableView();
        treeTableView.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));

        treeTableView.setShowRoot(false);
    }


    private <B,T> void setupCellValueFactory(JFXTreeTableColumn<B, T> column, Function<B, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<B, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private ObservableList<BillTreeObjectVo> generateDummyData(List<BillVo> billVoList) {
        ObservableList<BillTreeObjectVo> dummyData = FXCollections.observableArrayList();

        for(BillVo billVo : billVoList){
            dummyData.add(new BillTreeObjectVo(billVo));
        }
        return dummyData;
    }



    public void refreshButtonClicked(){
        setupBillTreeTableView();
    }

    public void deleteBillButtonClicked(){
        System.err.println("deleteBillButtonClicked");
        BillVo billVo = mainControllerAbstractImpl.getBillTreeTableView().getSelectionModel().getSelectedItem().getValue().getBillVo();

        System.err.println(billVo);

        BillDeleteDialog billDeleteDialog = new BillDeleteDialog(billVo);

        billDeleteDialog.getDialogBuilder().setPositiveBtn("确定", new DialogBuilder.OnClickListener() {
            @Override
            public void onClick() {
                System.err.println("删除账单!");
                billInformationService.deleteBill(billVo);
                setupBillTreeTableView();
            }
        });

        billDeleteDialog.show();
    }
}
