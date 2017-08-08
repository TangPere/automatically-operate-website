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
import java.io.Serializable;

/**
 * @author Pere H F DENG
 * @date 2017/7/28.
 */
@XmlRootElement(name = "Action")
public class Action implements Cloneable, Serializable {

    private Integer refOperateCode;

    private Long id;

    private Integer x;

    private Integer y;

    private Integer order;
    private Long waitTime;
    private String message;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(final Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(final Integer y) {
        this.y = y;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(final Integer order) {
        this.order = order;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(final Long waitTime) {
        this.waitTime = waitTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Integer getRefOperateCode() {
        return refOperateCode;
    }

    public void setRefOperateCode(final Integer refOperateCode) {
        this.refOperateCode = refOperateCode;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
