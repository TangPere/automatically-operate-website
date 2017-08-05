/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

/**
 * Created by Pere
 * Date 2017-05-24
 */
public class ImageUtil {

    public static void getTheScreenShot(WebDriver webDriver, String dir) throws IOException {

        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(dir + "/screen.png"));
    }

    public static void getTheScreenShot(WebDriver webDriver) throws IOException {

        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("screen.png"));
    }

    public static BufferedImage cutImage(BufferedImage srcImageBufferedImage, Integer x, Integer y, Integer width, Integer height) {
        return srcImageBufferedImage.getSubimage(x, y, width, height);
    }

    /**
     * <p>Title: cutImage</p>
     * <p>Description:  根据原图与裁切size截取局部图片</p>
     *
     * @param srcImg 源图片
     * @param output 图片输出流
     * @param rect   需要截取部分的坐标和大小
     */
    private static void cutImage(File srcImg, OutputStream output, java.awt.Rectangle rect) {

        if (srcImg.exists()) {
            FileInputStream fis = null;
            ImageInputStream iis = null;
            try {
                fis = new FileInputStream(srcImg);
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if (srcImg.getName().contains(".")) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if (suffix == null || !types.toLowerCase().contains(suffix.toLowerCase() + ",")) {
                    return;
                }
                // 将FileInputStream 转换为ImageInputStream
                iis = ImageIO.createImageInputStream(fis);
                // 根据图片类型获取该种类型的ImageReader
                ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, suffix, output);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null)
                        fis.close();
                    if (iis != null)
                        iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void cutImage(File srcImg, OutputStream output, int x, int y, int width, int height) {

        cutImage(srcImg, output, new java.awt.Rectangle(x, y, width, height));
    }

    private static void cutImage(File srcImg, String destImgPath, java.awt.Rectangle rect) {

        File destImg = new File(destImgPath);
        try {
            cutImage(srcImg, new FileOutputStream(destImg), rect);
        } catch (FileNotFoundException ignored) {
        }
    }

    public static void cutImage(File srcImg, String destImg, int x, int y, int width, int height) {

        cutImage(srcImg, destImg, new java.awt.Rectangle(x, y, width, height));
    }

    public static void cutImage(String srcImg, String destImg, int x, int y, int width, int height) {

        cutImage(new File(srcImg), destImg, new java.awt.Rectangle(x, y, width, height));
    }
}
