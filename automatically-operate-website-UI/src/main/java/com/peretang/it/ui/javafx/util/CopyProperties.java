/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.ui.javafx.util;

import com.peretang.it.operate.config.Action;
import com.peretang.it.operate.config.Config;
import com.peretang.it.operate.config.JudgeCondition;
import com.peretang.it.operate.config.Operate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pere H F DENG
 * @date 2017/7/31.
 */
public class CopyProperties {

    public static Config copyProopertiesFromEntity(com.peretang.it.data.entity.Config entityConfig) {
        Config operateConfig = new Config();

        // JudgeCondition
        List<JudgeCondition> operateJudgeConditionList = new ArrayList<>();

        Map<String, Integer> showMessage = new HashMap<>();
        for (com.peretang.it.data.entity.JudgeCondition entityJudgeCondition : entityConfig.getJudgeConditions()) {
            JudgeCondition operateJudgeCondition = new JudgeCondition();
            operateJudgeCondition.setRefOperateCode(entityJudgeCondition.getRefOperateCode());
            operateJudgeCondition.setShowMessageCode(String.valueOf(entityJudgeCondition.getId()));
            showMessage.put(String.valueOf(entityJudgeCondition.getId()), 0);
            operateJudgeCondition.setSourcePicPath(entityJudgeCondition.getSourcePicName());
            operateJudgeCondition.setWaitTime(entityJudgeCondition.getWaitTime());
            operateJudgeCondition.setX(entityJudgeCondition.getX());
            operateJudgeCondition.setY(entityJudgeCondition.getY());
            operateJudgeCondition.setWidth(entityJudgeCondition.getWidth());
            operateJudgeCondition.setHeight(entityJudgeCondition.getHeight());
            operateJudgeConditionList.add(operateJudgeCondition);
        }
        operateConfig.setJudgeConditions(operateJudgeConditionList);
        operateConfig.setShowMessageMap(showMessage);

        Map<Integer, Operate> operateMap = new HashMap<>();
        for (com.peretang.it.data.entity.Operate entityOperate : entityConfig.getOperates()) {
            Operate operate = new Operate();
            operate.setWaitTime(entityOperate.getWaitTime());
            operate.setOperateCode(entityOperate.getOperateCode());
            List<Action> actionList = new ArrayList<>();
            for (com.peretang.it.data.entity.Action entityAction : entityOperate.getActionList()) {
                Action action = new Action();
                action.setWaitTime(entityAction.getWaitTime());
                action.setX(entityAction.getX());
                action.setY(entityAction.getY());
                actionList.add(action);
            }
            operate.setActionList(actionList);

            operateMap.put(entityOperate.getOperateCode(), operate);
        }
        operateConfig.setOperateMap(operateMap);

        return operateConfig;
    }
}
