package com.hhr.accountbook.view;

import com.hhr.accountbook.controller.impl.MainControllerAbstractImpl;
import com.hhr.accountbook.util.ResourcesPathUtil;
import com.hhr.accountbook.util.RestStageSceneUtil;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import de.felixroske.jfxsupport.GUIState;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Harry
 * @Date: 2021/8/16 19:26
 * @Version 1.0
 */
@FXMLView(value = "/views/mainView.fxml")
public class MainView extends AbstractFxmlView {

    @Autowired
    private RestStageSceneUtil restStageSceneUtil;

    @Override
    public Parent getView() {
        restStageSceneUtil.restStageScene(1431,920);

        GUIState.getScene().getStylesheets().add(ResourcesPathUtil.getPathOfUrl("/css/jfoenix-components.css").toExternalForm());

        return super.getView();
    }

    @Autowired
    private MainControllerAbstractImpl mainControllerAbstractImpl;
}
