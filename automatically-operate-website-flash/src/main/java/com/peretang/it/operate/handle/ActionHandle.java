/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.handle;


import com.peretang.it.operate.action.FlashAction;
import com.peretang.it.operate.config.Action;
import com.peretang.it.operate.config.Operate;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Created by sro on 2017/7/26.
 */
public class ActionHandle {

    public static void getActionHandle(WebDriver webDriver, Operate operate, Map<String, Integer> map) {

        for (Action action : operate.getActionList()) {
            FlashAction.clickPoint(webDriver, action.getX(), action.getY());
        }

        for (String key : map.keySet()) {
            Integer value = map.get(key);
            map.put(key, ++ value);
        }

    }
}
