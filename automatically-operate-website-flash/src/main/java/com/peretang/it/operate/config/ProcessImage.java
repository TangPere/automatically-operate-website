/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.config;

import java.awt.image.BufferedImage;

/**
 * @author Pere H F DENG
 * @date 2017/9/21.
 */
public class ProcessImage {

    private BufferedImage bufferedImage;

    private Boolean finish;

    private Long processCode;
    private String strategyCode;

    private Long operateCode;

    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;

    public Long getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(final Long operateCode) {
        this.operateCode = operateCode;
    }

    public String getStrategyCode() {
        return strategyCode;
    }

    public void setStrategyCode(final String strategyCode) {
        this.strategyCode = strategyCode;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(final Boolean finish) {
        this.finish = finish;
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

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Long getProcessCode() {
        return processCode;
    }

    public void setProcessCode(final Long processCode) {
        this.processCode = processCode;
    }
}
