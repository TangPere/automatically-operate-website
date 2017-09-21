/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author Pere H F DENG
 * @date 2017/7/28.
 */
@XmlRootElement(name = "Config")
public class Config implements Cloneable, Serializable {

    private String name;

    private List<JudgeCondition> judgeConditions;

    private List<Operate> operates;

    private Integer defultValue;

    private Long defultWait;

    public Integer getDefultValue() {
        return defultValue;
    }

    public void setDefultValue(Integer defultValue) {
        this.defultValue = defultValue;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JudgeCondition> getJudgeConditions() {
        return judgeConditions;
    }

    public void setJudgeConditions(final List<JudgeCondition> judgeConditions) {
        this.judgeConditions = judgeConditions;
    }

    public List<Operate> getOperates() {
        return operates;
    }

    public void setOperates(final List<Operate> operates) {
        this.operates = operates;
    }

    public Long getDefultWait() {
        return defultWait;
    }

    public void setDefultWait(Long defultWait) {
        this.defultWait = defultWait;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
