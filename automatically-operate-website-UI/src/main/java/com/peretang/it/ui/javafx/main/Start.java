/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.ui.javafx.main;

import com.peretang.it.ui.javafx.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Pere H F DENG
 * @date 2017/7/31.
 */
public class Start extends Application {
    MainController mainController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/javafxdemo2.fxml"));
        Parent parent = root.load();
        mainController = root.getController();
        primaryStage.setTitle("My Application");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();

    }

    @Override
    public void stop() {
        mainController.stop();
    }
}
