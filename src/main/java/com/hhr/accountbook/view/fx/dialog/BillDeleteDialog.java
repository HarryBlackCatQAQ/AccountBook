package com.hhr.accountbook.view.fx.dialog;


import com.hhr.accountbook.vo.BillVo;
import de.felixroske.jfxsupport.GUIState;
import javafx.scene.control.Label;
import javafx.stage.Window;

import java.util.LinkedHashMap;

/**
 * @Author: Harry
 * @Date: 2021/8/19 20:11
 * @Version 1.0
 */
public class BillDeleteDialog extends MainDialog {

    public BillDeleteDialog(BillVo billVo){
        Window window = GUIState.getStage().getScene().getWindow();
        dialogBuilder = new DialogBuilder(window);

        Label label = new Label("确定要删除编号为: " + billVo.getId() + "的账单吗?");
        LinkedHashMap<String,Object> map = new LinkedHashMap<>();
        map.put("label",label);

        dialogBuilder.setNegativeBtn("取消", new DialogBuilder.OnClickListener() {
            @Override
            public void onClick() {

            }
        });

        dialogBuilder.setLayoutContentNodeMap(map);
    }
}
