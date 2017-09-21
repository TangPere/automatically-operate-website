/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.ui.javafx.controller;

import com.peretang.it.data.util.XMLUtil;
import com.peretang.it.entity.Action;
import com.peretang.it.entity.Config;
import com.peretang.it.entity.Operate;
import com.peretang.it.operate.browser.BrowserOperate;
import com.peretang.it.operate.proxy.ActionProxy;
import com.peretang.it.ui.javafx.util.CopyProperties;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.SerializationUtils;
import org.openqa.selenium.WebDriver;

import javax.xml.bind.JAXBException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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
    private Map<Long, TextField> showMessageMap = new HashMap<>();
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
        try {
            configMap = XMLUtil.getConfigMap("./XML");
        } catch (JAXBException ignored) {
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
        List<Operate> operates = selectedConfig.getOperates();
        for (Operate operate : operates) {
            Map<Integer, List<Action>> orderMap = operate.getActionList().stream().collect(Collectors.groupingBy(Action::getOrder));
            Map<Integer, Long> orderCountMap = operate.getActionList().stream()
                    .collect(Collectors.groupingBy(Action::getOrder, Collectors.counting()));
            List<Integer> countBigThan1List = new ArrayList<>();
            orderCountMap.forEach((integer, aLong) -> {
                if (aLong > 1) {
                    ToggleGroup group = new ToggleGroup();
                    toggleGroupMap.put(String.valueOf(operate.getOperateCode()) + "_" + String.valueOf(integer), group);
                    countBigThan1List.add(integer);
                }
            });
            for (Integer integer : countBigThan1List) {
                HBox actionOptionContains = new HBox();
                actionOptionContains.setAlignment(Pos.CENTER);
                actionOptionContains.setSpacing(20);
                orderMap.get(integer).forEach(action -> {
                    Label label = new Label(action.getMessage());
                    RadioButton button = new RadioButton();
                    button.setId(String.valueOf(action.getId()));
                    button.setToggleGroup(
                            toggleGroupMap.get(String.valueOf(operate.getOperateCode()) + "_" + String.valueOf(integer)));


                    actionOptionContains.setPrefWidth(100);
                    VBox actionOption = new VBox();
                    actionOption.setAlignment(Pos.CENTER);
                    actionOption.getChildren().addAll(label, button);

                    actionOptionContains.getChildren().add(actionOption);

                });
                actionOptions.getChildren().add(actionOptionContains);
            }
        }

        // Init the show message
        selectedConfig.getJudgeConditions().stream().filter(judgeCondition -> judgeCondition.getShowMessage() != null)
                .forEach(judgeCondition -> {
                    TextField messageField = new TextField();
                    messageField.setId(String.valueOf(judgeCondition.getId()));
                    messageField.setPrefWidth(50);
                    messageField.setEditable(false);

                    Label messageLabel = new Label(judgeCondition.getShowMessage());

                    VBox messageContains = new VBox();
                    messageContains.setAlignment(Pos.CENTER);
                    messageContains.getChildren().addAll(messageLabel, messageField);

                    showMessageMap.put(judgeCondition.getId(), messageField);

                    showMessages.getChildren().add(messageContains);
                });

        // Put count into showMessageMap
        TextField messageField = new TextField();
        messageField.setId("0");
        messageField.setPrefWidth(50);
        messageField.setEditable(false);
        Label messageLabel = new Label("总");
        VBox messageContains = new VBox();
        messageContains.setAlignment(Pos.CENTER);
        messageContains.getChildren().addAll(messageLabel, messageField);
        showMessageMap.put(0L, messageField);
        showMessages.getChildren().add(messageContains);

        // Init the Browser when go to console tab
        Properties browserProperties = new Properties();
        browserProperties.setProperty("browsePath", propertiesConfiguration.getString("browsePath"));

        webDriver = BrowserOperate.openBrowser(browserProperties);
    }

    private void start() {
        Map<Long, Integer> showMessageCodeMap = new HashMap<>();
        selectedConfig.getJudgeConditions().forEach(judgeCondition -> showMessageCodeMap.put(judgeCondition.getId(), 0));
        showMessageCodeMap.put(0L, 0);
        actionProxy.setMap(showMessageCodeMap);

        try {
            actionProxy.doProxy(webDriver, prepareActionConfig());
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

        // disable radio and target
        targetWebSitePath.setDisable(true);
        disableOrEnableRadioButton(true);

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
    }

    private void pauseOrResume() {
        if (pauseOrResume) {
            actionProxy.setThreadFlag(true);
            pauseOrResumeButton.setText("暂停");
            try {
                actionProxy.doProxy(webDriver, prepareActionConfig());
            } catch (Exception e) {
                e.printStackTrace();
            }

            disableOrEnableRadioButton(true);
            pauseOrResume = false;
        } else {
            // Stop the action keep message loop
            actionProxy.setThreadFlag(false);
            disableOrEnableRadioButton(false);
            pauseOrResumeButton.setText("继续");
            pauseOrResume = true;
        }
    }

    private com.peretang.it.operate.config.Config prepareActionConfig() throws CloneNotSupportedException {
        Config newConfig = SerializationUtils.clone(selectedConfig);
        toggleGroupMap.forEach((s, toggleGroup) -> {
            String[] idAndOrder = s.split("_");
            Long selectedActionId = Long.parseLong(((RadioButton) toggleGroup.getSelectedToggle()).getId());
            Integer operateCode = Integer.valueOf(idAndOrder[0]);

            Integer actionOrder = Integer.valueOf(idAndOrder[1]);

            List<Operate> operateList = newConfig.getOperates();
            Operate operate = operateList.stream().filter(o -> Objects.equals(o.getOperateCode(), operateCode)).findAny().get();

            Action selectAction = operate.getActionList().stream()
                    .filter(action1 -> Objects.equals(action1.getId(), selectedActionId)).findFirst().get();

            operate.getActionList().removeIf(action -> action.getOrder().equals(actionOrder));

            operate.getActionList().add(selectAction);
            operate.getActionList().sort(Comparator.comparingInt(Action::getOrder));

        });

        com.peretang.it.operate.config.Config operateConfig = CopyProperties.copyProopertiesFromEntity(newConfig);
        operateConfig.setWebSitePath(targetWebSitePath.getText());
        operateConfig.setDefultValue(selectedConfig.getDefultValue());
        operateConfig.setDefultWait(selectedConfig.getDefultWait());
        return operateConfig;
    }

    private void disableOrEnableRadioButton(Boolean disableOrEnable) {
        toggleGroupMap.forEach((s, toggleGroup) -> toggleGroup.getToggles().forEach(toggle -> ((RadioButton) toggle).setDisable(disableOrEnable)));
    }
}
