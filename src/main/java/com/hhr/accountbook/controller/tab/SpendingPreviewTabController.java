package com.hhr.accountbook.controller.tab;

import com.hhr.accountbook.controller.impl.MainControllerAbstractImpl;
import com.hhr.accountbook.services.SpendingPreviewService;
import com.hhr.accountbook.view.fx.DataPieChartManager;
import com.hhr.accountbook.vo.BillSpendingTypeVo;
import com.jfoenix.controls.JFXDatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/17 19:54
 * @Version 1.0
 */
@Slf4j
@Component
public class SpendingPreviewTabController {

    @Autowired
    private MainControllerAbstractImpl mainControllerAbstractImpl;

    @Autowired
    private SpendingPreviewService spendingPreviewService;

    private Label caption;

    public void initSpendingPreviewTab(){
        //初始化日期选择器
        initDatePicker();

        //初始化饼状图
        caption = new Label();
        initPieChart();

        //初始化总金额Label
        initSumMoneyLabel();
    }

    /**
     * 初始化日期选择器
     */
    private void initDatePicker(){
        initDatePicker(mainControllerAbstractImpl.getStartDatePicker());
        initDatePicker(mainControllerAbstractImpl.getEndDatePicker());
    }
    private void initDatePicker(JFXDatePicker jfxDatePicker){
        jfxDatePicker.setValue(LocalDate.now());
        jfxDatePicker.setShowWeekNumbers(true);
        jfxDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                initPieChart();
                initSumMoneyLabel();
            }
        });
    }

    /**
     * 初始化饼状图
     */
    private void initPieChart(){
        if(mainControllerAbstractImpl.getStartDatePicker().getValue().compareTo(mainControllerAbstractImpl.getEndDatePicker().getValue()) > 0){
            return;
        }

        List<BillSpendingTypeVo> billSpendingTypeVoList = spendingPreviewService.findAccountSpendTypeByTime(
                mainControllerAbstractImpl.getAccount(),
                mainControllerAbstractImpl.getStartDatePicker().getValue(),
                mainControllerAbstractImpl.getEndDatePicker().getValue());

//        List<BillVo> billVoList = spendingPreviewService.findAccountBillVoByTime(
//                mainControllerAbstractImpl.getAccount(),
//                mainControllerAbstractImpl.getStartDatePicker().getValue(),
//                mainControllerAbstractImpl.getEndDatePicker().getValue());


        caption.setText("");
        DataPieChartManager dataPieChartManager = new DataPieChartManager(mainControllerAbstractImpl.getDataPieChart(),caption);
        dataPieChartManager.addCaption(mainControllerAbstractImpl.getAnchorPaneOne());
        dataPieChartManager.setPieChartData(billSpendingTypeVoList);
    }

    /**
     * 初始化总金额Label
     */
    private void initSumMoneyLabel(){
        if(mainControllerAbstractImpl.getStartDatePicker().getValue().compareTo(mainControllerAbstractImpl.getEndDatePicker().getValue()) > 0){
            return;
        }

        Double sumMoney = spendingPreviewService.findAccountSumMoneyByTime(
                mainControllerAbstractImpl.getAccount(),
                mainControllerAbstractImpl.getStartDatePicker().getValue(),
                mainControllerAbstractImpl.getEndDatePicker().getValue());

        if(sumMoney != null){
            String sumMoneyText = String.format("%.2f",sumMoney);
            log.info("查询总额为:" + sumMoneyText);
            mainControllerAbstractImpl.getSumMoneyLabel().setText(sumMoneyText);
        }
        else{
            mainControllerAbstractImpl.getSumMoneyLabel().setText("");
        }
    }
}
