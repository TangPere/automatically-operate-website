/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.entity;

/**
 * @author Pere H F DENG
 * @date 2017/7/28.
 */
public class JudgeCondition {

    private String sourcePicName;

    private Integer refOperateCode;

    private Long id;

    private Integer x;

    private Integer y;
    private Integer width;
    private Integer height;
    private Long waitTime;

    private String showMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourcePicName() {
        return sourcePicName;
    }

    public void setSourcePicName(String sourcePicName) {
        this.sourcePicName = sourcePicName;
    }

    public Integer getRefOperateCode() {
        return refOperateCode;
    }

    public void setRefOperateCode(final Integer refOperateCode) {
        this.refOperateCode = refOperateCode;
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(final Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(final Integer height) {
        this.height = height;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(final Long waitTime) {
        this.waitTime = waitTime;
    }

    public String getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(final String showMessage) {
        this.showMessage = showMessage;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
