/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.config;

import java.util.List;

/**
 * @author Pere H F DENG
 * @date 2017/9/21.
 */
public class Process {

    private List<ProcessOptionalStateJudgmentImage> processOptionalStateJudgmentImageList;

    public List<ProcessOptionalStateJudgmentImage> getProcessOptionalStateJudgmentImageList() {
        return processOptionalStateJudgmentImageList;
    }

    public void setProcessOptionalStateJudgmentImageList(
        final List<ProcessOptionalStateJudgmentImage> processOptionalStateJudgmentImageList) {
        this.processOptionalStateJudgmentImageList = processOptionalStateJudgmentImageList;
    }
}
