/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.browser;

import com.google.gson.JsonObject;
import com.peretang.it.operate.constant.IConstant;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BrowserOperate {

    public static void initBrowser(Properties prop, JsonObject proxyData, DesiredCapabilities cap) {
        if (prop.containsKey(IConstant.PROXY_KEY)) {
            proxyData.addProperty(IConstant.PROXY_TYPE, IConstant.PROXY);
            proxyData.addProperty(IConstant.HTTP_PROXY, IConstant.PROXY_ADDRESS);
            proxyData.addProperty(IConstant.HTTP_PROXY_PORT, Integer.valueOf(IConstant.PROXY_PORT));
            cap.setCapability(CapabilityType.PROXY, proxyData);
        }

        if (prop.containsKey(IConstant.BROWSEPATH)) {
            System.setProperty(IConstant.FIREFOX_KEY, prop.getProperty("browsePath"));
        }
    }

    public static WebDriver openBrowser(Properties prop) {
        JsonObject proxyData = new JsonObject();
        DesiredCapabilities cap = new DesiredCapabilities();
        System.setProperty(IConstant.GECKO_KEY, IConstant.GECKO_VALUE);
        initBrowser(prop, proxyData, cap);

        WebDriver webDriver = new FirefoxDriver(cap);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return webDriver;
    }

    public static BufferedImage getScreenShot(WebDriver webDriver) {
        byte[] imageBytes = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
        ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);    //将b作为输入流；
        BufferedImage image = null;
        try {
            image = ImageIO.read(in);     //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
        } catch (IOException ignored) {
        }
        return image;
    }

    public static void closeBrowser(WebDriver webDriver) {
        webDriver.close();
    }
}
