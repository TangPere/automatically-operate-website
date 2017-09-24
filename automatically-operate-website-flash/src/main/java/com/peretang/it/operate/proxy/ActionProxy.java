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
import com.peretang.it.util.ImageUtil;
import com.peretang.it.util.SimilarImageUtil;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Created by sro on 2017/7/26.
 */
public class ActionProxy {

    private final Logger logger = Logger.getLogger(ActionProxy.class.getName());
    private Map<Integer, Integer> map;
    private boolean threadFlag = true;

    public void doProxy(WebDriver webDriver, Config config) throws Exception {
        WebSiteAction.switchToTargetWindow(webDriver, config.getWebSitePath());

      /*  config.getProcessMap().forEach((aLong, process) -> {
            process
        });*/

        //策略
        StringBuffer strategy = new StringBuffer("");

        Thread thread = new Thread(() -> {
            while (threadFlag) {
                // 开始一轮新的

                Integer count = map.get(0);
                count += 1;
                map.put(0, count);

                realAction(webDriver, config, strategy);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void realAction(WebDriver webDriver, Config config, final StringBuffer strategy) {

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

                if (SimilarImageUtil.isSimilar(processImage.getBufferedImage(), tempBufferedImage, processImage.getSimilarity())) {
                    if (processImage.getFinish()) {
                        strategy.append(processImage.getStrategyCode());
                        isRoundFinish = true;
                    }
                    if (map.containsKey(processImage.getMessageId())) {
                        Integer conut = map.get(processImage.getMessageId());
                        conut += 1;
                        map.put(processImage.getMessageId(), conut);
                    }
                    processCode = processImage.getProcessCode();
                    operateCode = processImage.getOperateCode();
                    try {
                        Thread.sleep(processImage.getWaitTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            StringBuffer optionalStateJudgment = null;
            if (config.getProcessMap().containsKey(processCode)) {
                // 截图
                screenShotBufferedImage = BrowserOperate.getScreenShot(webDriver);
                optionalStateJudgment = new StringBuffer();
                Process process = config.getProcessMap().get(processCode);
                for (ProcessOptionalStateJudgmentImage processOptionalStateJudgmentImage : process
                        .getProcessOptionalStateJudgmentImageList()) {
                    // 裁剪
                    BufferedImage tempBufferedImage = ImageUtil
                            .cutImage(screenShotBufferedImage, processOptionalStateJudgmentImage.getX(),
                                    processOptionalStateJudgmentImage.getY(), processOptionalStateJudgmentImage.getWidth(),
                                    processOptionalStateJudgmentImage.getHeight());
                    if (SimilarImageUtil.isSimilar(processOptionalStateJudgmentImage.getBufferedImage(), tempBufferedImage, processOptionalStateJudgmentImage.getSimilarity())) {
                        if (processOptionalStateJudgmentImage.getStrategyOrOptionalCode() == 1) {
                            optionalStateJudgment.append(processOptionalStateJudgmentImage.getOptionalStateJudgmentCode());
                        } else {
                            strategy.append(processOptionalStateJudgmentImage.getStrategyCode());
                        }
                        if (map.containsKey(processOptionalStateJudgmentImage.getMessageId())) {
                            Integer conut = map.get(processOptionalStateJudgmentImage.getMessageId());
                            conut += 1;
                            map.put(processOptionalStateJudgmentImage.getMessageId(), conut);
                        }

                    }
                }
            }


            Operate operate = config.getOperateMap().get(operateCode);
            logger.info("Operate: " + operateCode + " start,optionalStateJudgment: " + optionalStateJudgment + ",strategy: " + strategy);
            if (operate.getActionList().size() >= 1) {
                for (Action action : operate.getActionList()) {
                    Point point = null;

                    Integer subLenght = action.getStrategyOrOptionalCodeLenght();
                    if (action.getStrategyOrOptionalState() == 1L && optionalStateJudgment != null && optionalStateJudgment.length() >= subLenght) {
                        // use optionalState
                        point = action.getPointMap().get(optionalStateJudgment.substring(optionalStateJudgment.length() - subLenght));
                    } else if (action.getStrategyOrOptionalState() == 2L && strategy != null && strategy.length() >= subLenght) {
                        // Use strategy
                        point = action.getPointMap().get(strategy.substring(strategy.length() - subLenght));
                    }
                    if (point == null) {
                        // default
                        point = action.getPointMap().get("default");
                    }
                    FlashAction.clickPoint(webDriver, point.x, point.y);
                    try {
                        Thread.sleep(action.getWaitTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(operate.getWaitTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Operate: " + operateCode + " finish");
            if (isRoundFinish) {
                return;
            }
        }


    }

    public Map<Integer, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Integer> map) {
        this.map = map;
    }

    public void setThreadFlag(boolean threadFlag) {
        this.threadFlag = threadFlag;
    }


}
