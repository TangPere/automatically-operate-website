/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.proxy;

import com.peretang.it.operate.action.FlashAction;
import com.peretang.it.operate.action.WebSiteAction;
import com.peretang.it.operate.browser.BrowserOperate;
import com.peretang.it.operate.config.*;
import com.peretang.it.operate.config.Process;
import com.peretang.it.operate.constant.IConstant;
import com.peretang.it.util.ImageHelper;
import com.peretang.it.util.ImageUtil;
import com.peretang.it.util.SimilarImageUtil;
import org.openqa.selenium.WebDriver;

import java.awt.*;
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
        config.getJudgeConditions().forEach(judgeCondition -> judgeCondition
            .setSourceCandidateImage(ImageHelper.readPNGImage(IConstant.IMAGE_DIR + judgeCondition.getSourcePicPath())));

        Thread thread = new Thread(() -> {
            while (threadFlag) {
                // 开始一轮新的

                Integer showMessage = map.get(1L);
                showMessage += 1;
                map.put(1L, showMessage);

                //策略
                StringBuffer strategy = new StringBuffer("");
                realAction(webDriver, config, strategy);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void realAction(WebDriver webDriver, Config config, final StringBuffer strategy) {
        /*Integer operateCode = config.getDefultValue();
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
        }*/
        Boolean isRoundFinish = false;
        while (true) {
            Long processCode = null;
            Long operateCode = null;

            // 截图
            BufferedImage screenShotBufferedImage = BrowserOperate.getScreenShot(webDriver);
            // 判断执行到那一步
            for (ProcessImage processImage : config.getProcessImageList()) {
                // 裁剪
                BufferedImage tempBufferedImage = ImageUtil
                    .cutImage(screenShotBufferedImage, processImage.getX(), processImage.getY(), processImage.getWidth(),
                        processImage.getHeight());

                if (SimilarImageUtil.isSimilar(processImage.getBufferedImage(), tempBufferedImage)) {
                    if (processImage.getFinish()) {
                        strategy.append(processImage.getStrategyCode());
                        isRoundFinish = true;
                    }
                    processCode = processImage.getProcessCode();
                    operateCode = processImage.getOperateCode();
                    break;
                }
            }
            StringBuilder optionalStateJudgment = null;
            if (config.getProcessMap().containsKey(processCode)) {
                optionalStateJudgment = new StringBuilder();
                Process process = config.getProcessMap().get(processCode);
                for (ProcessOptionalStateJudgmentImage processOptionalStateJudgmentImage : process
                    .getProcessOptionalStateJudgmentImageList()) {
                    // 裁剪
                    BufferedImage tempBufferedImage = ImageUtil
                        .cutImage(screenShotBufferedImage, processOptionalStateJudgmentImage.getX(),
                            processOptionalStateJudgmentImage.getY(), processOptionalStateJudgmentImage.getWidth(),
                            processOptionalStateJudgmentImage.getHeight());
                    if (SimilarImageUtil.isSimilar(processOptionalStateJudgmentImage.getBufferedImage(), tempBufferedImage)) {
                        optionalStateJudgment.append(processOptionalStateJudgmentImage.getOptionalStateJudgmentCode());
                    }
                }
            }

            Operate operate = config.getOperateMap().get(operateCode);
            for (Action action : operate.getActionList()) {
                Point point = null;

                Integer subLenght = action.getStrategyOrOptionalCodeLenght();
                if (action.getStrategyOrOptionalState() == 0L && optionalStateJudgment != null
                    && optionalStateJudgment.length() >= subLenght) {
                    // use optionalState
                    point = action.getPointMap().get(optionalStateJudgment.substring(optionalStateJudgment.length() - subLenght));
                } else if (action.getStrategyOrOptionalState() == 1L && strategy != null && strategy.length() >= subLenght) {
                    // Use strategy
                    point = action.getPointMap().get(strategy.substring(strategy.length() - subLenght));
                }
                if (point == null) {
                    // default
                    point = action.getPointMap().get("default");
                }
                FlashAction.clickPoint(webDriver, point.x, point.y);
            }

            if (isRoundFinish) {
                return;
            }
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
