package com.hhr.accountbook.view.fx.dialog;

import com.jfoenix.controls.JFXSpinner;
import de.felixroske.jfxsupport.GUIState;
import javafx.application.Platform;
import javafx.stage.Window;

import java.util.LinkedHashMap;

/**
 * @Author: Harry
 * @Date: 2021/8/16 6:28
 * @Version 1.0
 */

public class LoadingDialog extends MainDialog{

    public LoadingDialog(){
        Window window = GUIState.getStage().getScene().getWindow();
        dialogBuilder = new DialogBuilder(window);
        JFXSpinner loading = new JFXSpinner();
        loading.setPrefWidth(1000);
        loading.setPrefHeight(1000);
        LinkedHashMap<String,Object> map = new LinkedHashMap<>();
        map.put("loading",loading);
        dialogBuilder.setLayoutContentNodeMap(map);
        dialogBuilder.getLayout().setPrefHeight(220);
        dialogBuilder.getLayout().setPrefWidth(220);
    }

    @Override
    public void show() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.super.show();
            }
        });
    }

    @Override
    public void close() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.super.close();
            }
        });
    }
}
