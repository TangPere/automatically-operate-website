/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.action;

import com.peretang.it.util.ImageUtil;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * Created by Pere
 * Date 2017-07-20
 */
public class WebSiteAction {

    public static void getAreaFromWebSite(WebDriver webDriver, String dir, Integer x, Integer y, Integer width, Integer height) {

        // Get screen shot
        try {
            ImageUtil.getTheScreenShot(webDriver, dir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Cut it
        // TODO:Need to search in some DB to get different position about VerifyCode in different website
        ImageUtil.cutImage(dir + "/screen.png", dir + "/temp.png", x, y, width, height);
    }

    public static void switchToTargetWindow(WebDriver webDriver, String targetUrl) {

        for (String hanld : webDriver.getWindowHandles()) {
            if (webDriver.switchTo().window(hanld).getCurrentUrl().equals(targetUrl)) {
                break;
            }
        }
    }

}
