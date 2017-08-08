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
import com.peretang.it.operate.browser.BrowserOperate;
import com.peretang.it.operate.config.Config;
import com.peretang.it.operate.config.JudgeCondition;
import com.peretang.it.operate.config.Operate;
import com.peretang.it.operate.constant.IConstant;
import com.peretang.it.operate.handle.ActionHandle;
import com.peretang.it.util.ImageHelper;
import com.peretang.it.util.ImageUtil;
import com.peretang.it.util.SimilarImageUtil;
import org.openqa.selenium.WebDriver;

import java.awt.image.BufferedImage;
import java.util.Map;


/**
 * Created by sro on 2017/7/26.
 */
public class ActionProxy {

    private Map<Long, Integer> map;

    private boolean threadFlag = true;

    public void doProxy(WebDriver webDriver, Config config) throws Exception {
        WebSiteAction.switchToTargetWindow(webDriver, config.getWebSitePath());

        // 初始化对比对象
        config.getJudgeConditions().forEach(judgeCondition -> judgeCondition.setSourceCandidateImage(ImageHelper.readPNGImage(IConstant.IMAGE_DIR + judgeCondition.getSourcePicPath())));

        Thread thread = new Thread(() -> {
            while (threadFlag) {
                realAction(webDriver, config);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void realAction(WebDriver webDriver, Config config) {
        Integer operateCode = config.getDefultValue();
        Boolean defultFlag = true;


        // 截图
        BufferedImage screenShotBufferedImage = BrowserOperate.getScreenShot(webDriver);

        for (JudgeCondition judgeCondition : config.getJudgeConditions()) {
            // 裁剪
            BufferedImage tempBufferedImage = ImageUtil.cutImage(screenShotBufferedImage, judgeCondition.getX(), judgeCondition.getY(),
                    judgeCondition.getWidth(), judgeCondition.getHeight());

            // 对比
            if (SimilarImageUtil.isSimilar(judgeCondition.getSourceCandidateImage(), tempBufferedImage)) {
                // 获取结果
                // 设置操作代码
                operateCode = judgeCondition.getRefOperateCode();

                // 操作统计数据
                Integer showMessage = map.get(judgeCondition.getShowMessageCode());
                showMessage += 1;
                map.put(judgeCondition.getShowMessageCode(), showMessage);

                // 操作的等待时间
                try {
                    Thread.sleep(judgeCondition.getWaitTime());
                } catch (InterruptedException ignored) {
                }
                defultFlag = false;
                break;
            }


        }
        Integer count = map.get(0L);
        count += 1;
        map.put(0L, count);
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

    public Map<Long, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Long, Integer> map) {
        this.map = map;
    }

    public void setThreadFlag(boolean threadFlag) {
        this.threadFlag = threadFlag;
    }


}
