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

import java.awt.image.BufferedImage;

/**
 * 判断对象
 *
 * @author Pere H F DENG
 * @date 2017/7/28.
 */
public class JudgeCondition {

    private String sourcePicPath;

    private Integer refOperateCode;

    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;

    private Long waitTime;

    private String showMessageCode;

    private BufferedImage sourceCandidateImage;

    public BufferedImage getSourceCandidateImage() {
        return sourceCandidateImage;
    }

    public void setSourceCandidateImage(BufferedImage sourceCandidateImage) {
        this.sourceCandidateImage = sourceCandidateImage;
    }

    public String getSourcePicPath() {
        return sourcePicPath;
    }

    public void setSourcePicPath(String sourcePicPath) {
        this.sourcePicPath = sourcePicPath;
    }

    public Integer getRefOperateCode() {
        return refOperateCode;
    }

    public void setRefOperateCode(Integer refOperateCode) {
        this.refOperateCode = refOperateCode;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
    }

    public String getShowMessageCode() {
        return showMessageCode;
    }

    public void setShowMessageCode(String showMessageCode) {
        this.showMessageCode = showMessageCode;
    }
}
