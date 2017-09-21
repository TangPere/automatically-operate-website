/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.config;

import java.awt.*;
import java.util.Map;

/**
 * 动作对象
 *
 * @author Pere H F DENG
 * @date 2017/7/28.
 */
public class Action {

    private Long strategyOrOptionalState;

    private Integer strategyOrOptionalCodeLenght;

    private Map<String, Point> pointMap;
    private Long waitTime;

    public Integer getStrategyOrOptionalCodeLenght() {
        return strategyOrOptionalCodeLenght;
    }

    public void setStrategyOrOptionalCodeLenght(final Integer strategyOrOptionalCodeLenght) {
        this.strategyOrOptionalCodeLenght = strategyOrOptionalCodeLenght;
    }

    public Long getStrategyOrOptionalState() {
        return strategyOrOptionalState;
    }

    public void setStrategyOrOptionalState(final Long strategyOrOptionalState) {
        this.strategyOrOptionalState = strategyOrOptionalState;
    }

    public Map<String, Point> getPointMap() {
        return pointMap;
    }

    public void setPointMap(final Map<String, Point> pointMap) {
        this.pointMap = pointMap;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
    }
}
