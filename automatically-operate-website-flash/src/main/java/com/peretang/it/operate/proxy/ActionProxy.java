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

    private Map<String, Integer> map;

    private boolean threadFlag = true;

    public Thread doProxy(WebDriver webDriver, Config config) throws Exception {
        WebSiteAction.switchToTargetWindow(webDriver, config.getWebSitePath());
        map = config.getShowMessageMap();

        Thread thread = new Thread(() -> {

            while (threadFlag) {
                Integer operateCode = config.getDefultValue();
                Boolean defultFlag = true;

                for (JudgeCondition judgeCondition : config.getJudgeConditions()) {
                    WebSiteAction
                            .getAreaFromWebSite(webDriver, IConstant.TEMP_DIR, judgeCondition.getX(), judgeCondition.getY(),
                                    judgeCondition.getWidth(), judgeCondition.getHeight());
                    if (SimilarImageUtil.isSimilar(IConstant.IMAGE_DIR + judgeCondition.getSourcePicPath(), IConstant.TEMP_FILE)) {
                        operateCode = judgeCondition.getRefOperateCode();
                        Integer showmessage = map.get(judgeCondition.getShowMessageCode());
                        showmessage += 1;
                        map.put(judgeCondition.getShowMessageCode(), showmessage);
                        try {
                            Thread.sleep(judgeCondition.getWaitTime());
                        } catch (InterruptedException ignored) {
                        }
                        defultFlag = false;
                        break;
                    }
                }
                Integer count = map.get("0");
                count += 1;
                map.put("0", count);
                if (defultFlag) {
                    try {
                        Thread.sleep(config.getDefultWait());
                    } catch (InterruptedException ignored) {
                    }
                }

                Operate operate = config.getOperateMap().get(operateCode);
                ActionHandle.getActionHandle(webDriver, operate);

                try {
                    Thread.sleep(operate.getWaitTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return thread;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setThreadFlag(boolean threadFlag) {
        this.threadFlag = threadFlag;
    }


}
