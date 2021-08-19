package com.hhr.accountbook.view.fx;

import com.hhr.accountbook.info.AccountBookInfo;
import com.hhr.accountbook.util.ResourcesPathUtil;
import com.hhr.accountbook.view.fx.dialog.StageCloseDialog;
import de.felixroske.jfxsupport.GUIState;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @Author: Harry
 * @Date: 2021/8/19 20:33
 * @Version 1.0
 */
public class MyStage implements AccountBookInfo {

    public static void initStage(Stage stage){
        stage.setResizable(false);

        //设置标题
        stage.setTitle(STAGE_TITLE);

        //设置托盘
        MySystemTray.getInstance().listen(stage);

        //设置界面关闭弹出监听
        setOnCloseRequest(stage);

    }

    /**
     * set OnCloseRequest
     */
    private static void setOnCloseRequest(Stage stage){
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                StageCloseDialog stageCloseDialog = new StageCloseDialog();
                stageCloseDialog.show();
            }
        });
    }
}
