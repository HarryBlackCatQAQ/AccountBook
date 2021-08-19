package com.hhr.accountbook.view.fx.dialog;

import lombok.Getter;

/**
 * @Author: Harry
 * @Date: 2021/8/11 21:56
 * @Version 1.0
 */
@Getter
public abstract class MainDialog {
    protected DialogBuilder dialogBuilder;

    public void show(){
        this.dialogBuilder.create();
    }

    public void close(){
        this.dialogBuilder.getAlert().close();
    }
}
