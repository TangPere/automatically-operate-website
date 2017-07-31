/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.proxy;

import com.peretang.it.operate.action.WebSiteAction;
import com.peretang.it.operate.config.Config;
import com.peretang.it.operate.config.JudgeCondition;
import com.peretang.it.operate.config.Operate;
import com.peretang.it.operate.constant.IConstant;
import com.peretang.it.operate.handle.ActionHandle;
import com.peretang.it.util.SimilarImageUtil;
import org.openqa.selenium.WebDriver;

import java.util.Map;


/**
 * Created by sro on 2017/7/26.
 */
public class ActionProxy {

    public static void getActionProxy(WebDriver webDriver, Config config) throws Exception {
        SimilarImageUtil similarImageUtil = new SimilarImageUtil(config.getWebSitePath() + "\\result.png");

        while (true) {
            for (JudgeCondition judgeCondition : config.getJudgeConditions()) {

                WebSiteAction
                    .getAreaFromWebSite(webDriver, judgeCondition.getSourcePicPath(), judgeCondition.getX(), judgeCondition.getY(),
                        judgeCondition.getHeight(), judgeCondition.getWidth());

                if (similarImageUtil.isSimilar(IConstant.TEMP_DIR + IConstant.TARGET_FILE_NAME)) {
                    Thread.sleep(judgeCondition.getWaitTime());
                    Operate operate = config.getOperateMap().get(judgeCondition.getRefOperateCode());
                    Map<String, Integer> map = config.getShowMessageMap();

                    ActionHandle.getActionHandle(webDriver, operate, map);

                    Thread.sleep(operate.getWaitTime());
                    break;
                }
            }
        }
    }
}
