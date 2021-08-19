package com.hhr.accountbook.util;

import de.felixroske.jfxsupport.GUIState;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 * @Author: Harry
 * @Date: 2021/8/17 2:34
 * @Version 1.0
 */
@Component
public class RestStageSceneUtil {

    public void restStageScene(double width,double height){
        Stage stage = GUIState.getStage();
        stage.setWidth(width);
        stage.setHeight(height);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }
}
