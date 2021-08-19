package com.hhr.accountbook.view.fx.dialog;


import com.hhr.accountbook.model.Account;
import de.felixroske.jfxsupport.GUIState;
import javafx.scene.control.Label;
import javafx.stage.Window;

import java.util.LinkedHashMap;

/**
 * @Author: Harry
 * @Date: 2021/8/18 1:15
 * @Version 1.0
 */
public class AccountDeleteDialog extends MainDialog {

    public AccountDeleteDialog(Account account){
        Window window = GUIState.getStage().getScene().getWindow();
        dialogBuilder = new DialogBuilder(window);

        Label label = new Label("确定要删除用户: " + account.getAccountName() + " 吗?");
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
