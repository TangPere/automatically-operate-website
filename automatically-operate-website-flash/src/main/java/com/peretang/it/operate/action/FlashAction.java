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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by Pere
 * Date 2017-07-23
 */
public class FlashAction {

    public static void clickPoint(WebDriver webDriver, int x, int y) {

        Actions actions = new Actions(webDriver);
        actions.moveByOffset(x, y).click().moveByOffset(- x, - y).perform();
    }
}
