/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.constant;

import com.peretang.it.operate.singleton.PorpSingleton;

/**
 * Created by sro on 2017/7/25.
 */
public final class IConstant {
    public final static String UTF = "UTF-8";

    public final static String ACTION_X = "x";
    public final static String ACTION_Y = "y";

    public final static String PROXY_KEY = "proxy";
    public final static String PROXY_TYPE = "proxyType";
    public final static String HTTP_PROXY = "httpProxy";
    public final static String HTTP_PROXY_PORT = "httpProxyPort";

    public final static String BROWSEPATH = "browsePath";

    public final static String GECKO_KEY = "webdriver.gecko.driver";
    public final static String FIREFOX_KEY = "webdriver.firefox.bin";


    public final static String GECKO_VALUE = "WebDriver/geckodriver.exe";

    public final static String FIREFOX_VALUE = PorpSingleton.getProperties().getProperty("browsePath");

    public final static String PROXY = PorpSingleton.getProperties().getProperty("proxy");
    public final static String PROXY_ADDRESS = PorpSingleton.getProperties().getProperty("proxy_address");
    public final static String PROXY_PORT = PorpSingleton.getProperties().getProperty("proxy_port");

    public final static String TARGET_URL = PorpSingleton.getProperties().getProperty("targetURL");

    public final static String NUMBER_OF_ACTION = PorpSingleton.getProperties().getProperty("number_of_action");

    public final static String CUT_X = PorpSingleton.getProperties().getProperty("cut_x");
    public final static String CUT_Y = PorpSingleton.getProperties().getProperty("cut_y");

    public final static String CUT_HEIGHT = PorpSingleton.getProperties().getProperty("cut_height");
    public final static String CUT_WIDTH = PorpSingleton.getProperties().getProperty("cut_width");

    public final static String TEMP_DIR = "./temp/";
    public final static String IMAGE_DIR = "./image/";
    public final static String TEMP_FILE_NAME = "temp.png";
    public final static String TEMP_FILE = TEMP_DIR + TEMP_FILE_NAME;

    public final static String MESSAGE = PorpSingleton.getProperties().getProperty("message");
    public final static String SIMILAR_MESSAGE = PorpSingleton.getProperties().getProperty("similarMessage");

    public final static String CLICK_X = PorpSingleton.getProperties().getProperty("click_x");
    public final static String CLICK_Y = PorpSingleton.getProperties().getProperty("click_y");
}
