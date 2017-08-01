/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.config;

import java.util.List;
import java.util.Map;

/**
 * 配置对象
 *
 * @author Pere H F DENG
 * @date 2017/7/28.
 */
public class Config {

    private String webSitePath;

    private Map<String, Integer> showMessageMap;

    private List<JudgeCondition> judgeConditions;

    private Map<Integer, Operate> operateMap;

    private Integer defultValue;

    private Long defultWait;

    public Long getDefultWait() {
        return defultWait;
    }

    public void setDefultWait(Long defultWait) {
        this.defultWait = defultWait;
    }

    public Integer getDefultValue() {
        return defultValue;
    }

    public void setDefultValue(Integer defultValue) {
        this.defultValue = defultValue;
    }


    public String getWebSitePath() {
        return webSitePath;
    }

    public void setWebSitePath(String webSitePath) {
        this.webSitePath = webSitePath;
    }

    public Map<String, Integer> getShowMessageMap() {
        return showMessageMap;
    }

    public void setShowMessageMap(Map<String, Integer> showMessageMap) {
        this.showMessageMap = showMessageMap;
    }

    public List<JudgeCondition> getJudgeConditions() {
        return judgeConditions;
    }

    public void setJudgeConditions(List<JudgeCondition> judgeConditions) {
        this.judgeConditions = judgeConditions;
    }

    public Map<Integer, Operate> getOperateMap() {
        return operateMap;
    }

    public void setOperateMap(Map<Integer, Operate> operateMap) {
        this.operateMap = operateMap;
    }
}
