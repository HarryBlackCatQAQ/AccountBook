package com.hhr.accountbook.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Builder;
import lombok.Data;


/**
 * @Author: Harry
 * @Date: 2021/8/18 18:21
 * @Version 1.0
 */
@Data
public class BillTreeObjectVo extends RecursiveTreeObject<BillTreeObjectVo> {
    private StringProperty id;
    private StringProperty accountName;
    private StringProperty details;
    private StringProperty payMethod;
    private StringProperty spendingType;
    private StringProperty payTime;
    private StringProperty money;

    private BillVo billVo;

    public BillTreeObjectVo(BillVo billVo) {
        this.billVo = billVo;

        this.id = new SimpleStringProperty(billVo.getId());
        this.accountName = new SimpleStringProperty(billVo.getAccountName());
        this.details = new SimpleStringProperty(billVo.getDetails());
        this.payMethod = new SimpleStringProperty(billVo.getPayMethod());
        this.spendingType = new SimpleStringProperty(billVo.getSpendingType());
        this.payTime = new SimpleStringProperty(billVo.getPayTime().toString());
        this.money = new SimpleStringProperty(billVo.getMoney().toString());
    }
}
