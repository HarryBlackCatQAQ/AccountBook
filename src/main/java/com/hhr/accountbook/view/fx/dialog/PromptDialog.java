package com.hhr.accountbook.view.fx.dialog;

import de.felixroske.jfxsupport.GUIState;
import javafx.scene.control.Label;
import javafx.stage.Window;

import java.util.LinkedHashMap;

/**
 * @Author: Harry
 * @Date: 2021/8/16 18:36
 * @Version 1.0
 */
public class PromptDialog extends MainDialog{

    public PromptDialog(String prompt){
        Window window = GUIState.getStage().getScene().getWindow();
        dialogBuilder = new DialogBuilder(window);

        Label promptLabel = new Label(prompt);
        LinkedHashMap<String,Object> map = new LinkedHashMap<>();
        map.put("promptLabel",promptLabel);

        //设置提示窗口按钮和其监听事件
        dialogBuilder.setPositiveBtn("确定", new DialogBuilder.OnClickListener() {
            @Override
            public void onClick() {
                dialogBuilder.getAlert().close();
            }
        });

        dialogBuilder.setLayoutContentNodeMap(map);
    }
}
