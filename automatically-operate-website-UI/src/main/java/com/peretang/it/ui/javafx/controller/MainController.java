/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.ui.javafx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peretang.it.operate.browser.BrowserOperate;
import com.peretang.it.operate.config.Config;
import com.peretang.it.operate.proxy.ActionProxy;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author Pere H F DENG
 * @date 2017/7/28.
 */
public class MainController implements Initializable {

    public TabPane tab;
    public ChoiceBox<String> configList;
    public Button goToConsoleTabButton;
    public Tab consoleTab;
    public HBox showMessages;
    public VBox actionOptions;
    public Button startProcessButton;
    public Button stopProcessButton;
    public TextField browserPath;
    public Button confirmBrowserPathButton;
    public Tab configListTab;
    public Tab analyzingConditionsTab;
    public Tab confirmBrowserPathTab;
    public TextField targetWebSitePath;
    public Button pauseOrResumeButton;

    private Map<String, Config> configMap;

    private Config selectedConfig;

    private Map<String, ToggleGroup> toggleGroupMap = new HashMap<>();
    private Map<Integer, TextField> showMessageMap = new HashMap<>();
    private PropertiesConfiguration propertiesConfiguration;

    private WebDriver webDriver;

    private ActionProxy actionProxy = new ActionProxy();

    private boolean threadFlag = true;

    private boolean pauseOrResume = false;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        Boolean adminMode = false;
        // TODO: Initialize the interface according to permissions

        if (adminMode) {
            tab.getSelectionModel().select(analyzingConditionsTab);
        } else {
            tab.getTabs().remove(0, 3);

            // Find config for Browser path
            try {
                propertiesConfiguration = new PropertiesConfiguration("./settings/config.properties");
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }
            propertiesConfiguration.setEncoding("UTF-8");
            String browsePath = propertiesConfiguration.getString("browsePath");
            if ("".equals(browsePath) || !browsePath.endsWith("firefox.exe")) {
                tab.getSelectionModel().select(confirmBrowserPathTab);
                configListTab.setDisable(true);
            } else {
                tab.getTabs().remove(confirmBrowserPathTab);
                tab.getSelectionModel().select(configListTab);
            }

        }
        goToConsoleTabButton.setDisable(true);
        consoleTab.setDisable(true);
        stopProcessButton.setDisable(true);
        pauseOrResumeButton.setDisable(true);

        // Find Configuration in data base or XML

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("./json");
        File[] jsonFiles = file.listFiles((dir, name) -> name.endsWith(".json"));
        if (jsonFiles != null) {
            configMap = new HashMap<>();
            for (File jsonFile : jsonFiles) {
                try {
                    configMap.put(jsonFile.getName().substring(0, jsonFile.getName().lastIndexOf(".")), objectMapper.readValue(jsonFile, Config.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        configList.setItems(FXCollections.observableArrayList(configMap.keySet()));

        // Configure the listener
        configList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedConfig = configMap.get(newValue);
            goToConsoleTabButton.setDisable(false);
        });
        goToConsoleTabButton.setOnAction(event -> {
            consoleTab.setDisable(false);
            tab.getSelectionModel().select(consoleTab);
            configListTab.setDisable(true);
            initTheConsoleTab();
        });
        final PropertiesConfiguration finalConfig = propertiesConfiguration;
        confirmBrowserPathButton.setOnAction(event -> validTextFieldAndSaveConfig(finalConfig));

        startProcessButton.setOnAction(event -> start());
        stopProcessButton.setOnAction(event -> stop());
        pauseOrResumeButton.setOnAction(event -> pauseOrResume());
    }

    private void validTextFieldAndSaveConfig(final PropertiesConfiguration finalConfig) {
        if (!"".equals(browserPath.getText()) && browserPath.getText().endsWith("firefox.exe")) {
            String browserPathStr = browserPath.getText().replaceAll("\\\\", "/");
            finalConfig.setProperty("browsePath", browserPathStr);
            try {
                finalConfig.save();
            } catch (ConfigurationException ignored) {
            }
            confirmBrowserPathTab.setDisable(true);
            configListTab.setDisable(false);
            tab.getSelectionModel().select(configListTab);
        }
    }

    private void initTheConsoleTab() {


        // Init the show message
        selectedConfig.getShowMessageMap().forEach((integer, s) -> {
            TextField messageField = new TextField();
            messageField.setId(String.valueOf(integer));
            messageField.setPrefWidth(50);
            messageField.setEditable(false);

            Label messageLabel = new Label(s);

            VBox messageContains = new VBox();
            messageContains.setAlignment(Pos.CENTER);
            messageContains.getChildren().addAll(messageLabel, messageField);

            showMessageMap.put(integer, messageField);

            showMessages.getChildren().add(messageContains);
        });

        // Init the Browser when go to console tab
        Properties browserProperties = new Properties();
        browserProperties.setProperty("browsePath", propertiesConfiguration.getString("browsePath"));

        webDriver = BrowserOperate.openBrowser(browserProperties);
    }

    private void start() {
        Map<Integer, Integer> showMessageCodeMap = new HashMap<>();
        selectedConfig.getShowMessageMap().forEach((integer, s) -> {
            showMessageCodeMap.put(integer, 0);
        });
        actionProxy.setMap(showMessageCodeMap);

        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().substring(0, 10);

        try {
            actionProxy.doProxy(webDriver, selectedConfig);
        } catch (Exception ignored) {
        }

        showMessageMap.forEach((s, textField) -> {
            // new Task
            Task<String> loopMessageTask = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    while (threadFlag) {
                        updateValue(Integer.toString(actionProxy.getMap().get(s)));
                        Thread.sleep(2000);
                    }
                    return String.valueOf(actionProxy.getMap().get(s));
                }
            };
            textField.textProperty().bind(loopMessageTask.valueProperty());
            Thread thread = new Thread(loopMessageTask);
            thread.setDaemon(true);
            thread.start();
        });

        /*Thread thread = new Thread(() -> {
            while (threadFlag) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", id);
                StringBuffer messageBuffer = new StringBuffer();
                selectedConfig.getShowMessageMap().forEach((integer, textField) -> {
                    messageBuffer.append(textField).append(":").append(actionProxy.getMap().get(integer)).append(",");
                });
                jsonObject.addProperty("message", messageBuffer.toString());
                post(jsonObject.toString());
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException ignored) {
                }
            }
        });
        thread.setDaemon(true);
        thread.start();*/

        // disable radio and target
        targetWebSitePath.setDisable(true);

        pauseOrResumeButton.setDisable(false);
        stopProcessButton.setDisable(false);
        startProcessButton.setDisable(true);
    }

    public void stop() {
        threadFlag = false;
        actionProxy.setThreadFlag(false);
        webDriver.quit();


        pauseOrResumeButton.setDisable(true);
        stopProcessButton.setDisable(true);
        startProcessButton.setDisable(true);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ignored) {
        }
    }

    private void pauseOrResume() {
        if (pauseOrResume) {
            actionProxy.setThreadFlag(true);
            pauseOrResumeButton.setText("暂停");
            try {
                actionProxy.doProxy(webDriver, selectedConfig);
            } catch (Exception e) {
                e.printStackTrace();
            }

            pauseOrResume = false;
        } else {
            // Stop the action keep message loop
            actionProxy.setThreadFlag(false);
            pauseOrResumeButton.setText("继续");
            pauseOrResume = true;
        }
    }

    /**
     * 调用 API
     *
     * @param parameters
     * @return
     */
    public void post(String parameters) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://7nq89to7il.execute-api.ap-northeast-2.amazonaws.com/prod/monitor");

        try {

            // 建立一个NameValuePair数组，用于存储欲传送的参数
            post.addHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));

            client.execute(post);


        } catch (IOException ignored) {
        }
    }

}
