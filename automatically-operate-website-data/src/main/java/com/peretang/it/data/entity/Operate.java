/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.data.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Pere H F DENG
 * @date 2017/7/28.
 */
@XmlRootElement(name = "Operate")
public class Operate {

    private Integer operateCode;

    private List<Action> actionList;

    private Long waitTime;

    public Integer getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(final Integer operateCode) {
        this.operateCode = operateCode;
    }

    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(final List<Action> actionList) {
        this.actionList = actionList;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(final Long waitTime) {
        this.waitTime = waitTime;
    }
}
