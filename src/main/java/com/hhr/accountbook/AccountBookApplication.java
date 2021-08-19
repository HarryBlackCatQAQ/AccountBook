package com.hhr.accountbook;

import com.hhr.accountbook.info.AccountBookInfo;
import com.hhr.accountbook.util.ResourcesPathUtil;
import com.hhr.accountbook.view.LoginView;
import com.hhr.accountbook.view.StartUpView;
import com.hhr.accountbook.view.fx.MyStage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author: Harry
 * @Date: 2021/8/15 20:19
 * @Version 1.0
 */
@SpringBootApplication
@Slf4j
public class AccountBookApplication extends AbstractJavaFxApplicationSupport implements AccountBookInfo {

    public static void main(String[] args) {
        launch(AccountBookApplication.class, LoginView.class,new StartUpView(),args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        MyStage.initStage(stage);
    }

    /**
     * 设置图标
     */
    @Override
    public Collection<Image> loadDefaultIcons() {
        return Collections.singletonList(new Image(ResourcesPathUtil.getPathOfString(ICON_PATH)));
    }


    /**
     * 重启程序
     */
    public void relaunch(){
        Platform.runLater(() -> {
            // 关闭窗口
            getStage().close();
            try {
                // 关闭ApplicationContext
                this.stop();
                // 重新初始化
                this.init();
                this.start(new Stage());
            } catch (Exception e) {
                log.error("重启系统失败!" + e);
            }
        });
    }



    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
        log.info("jpaQueryFactory注入");
        return new JPAQueryFactory(entityManager);
    }
}
