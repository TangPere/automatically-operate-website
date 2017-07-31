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

import java.awt.image.BufferedImage;

public class HistogramFilter {

    private int redBins;

    private int greenBins;

    private int blueBins;

    public HistogramFilter() {

        redBins = greenBins = blueBins = 4;
    }

    public void setRedBinCount(int redBinCount) {

        this.redBins = redBinCount;
    }

    public void setGreenBinCount(int greenBinCount) {

        this.greenBins = greenBinCount;
    }

    public void setBlueBinCount(int blueBinCount) {

        this.blueBins = blueBinCount;
    }

    public float[] filter(BufferedImage src) {

        int width = src.getWidth();
        int height = src.getHeight();

        int[] inPixels = new int[width * height];
        float[] histogramData = new float[redBins * greenBins * blueBins];
        getRGB(src, 0, 0, width, height, inPixels);
        int index = 0;
        int redIdx = 0, greenIdx = 0, blueIdx = 0;
        int singleIndex = 0;
        float total = 0;
        for (int row = 0; row < height; row++) {
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                ta = (inPixels[index] >> 24) & 0xff;
                tr = (inPixels[index] >> 16) & 0xff;
                tg = (inPixels[index] >> 8) & 0xff;
                tb = inPixels[index] & 0xff;
                redIdx = (int) getBinIndex(redBins, tr, 255);
                greenIdx = (int) getBinIndex(greenBins, tg, 255);
                blueIdx = (int) getBinIndex(blueBins, tb, 255);
                singleIndex = redIdx + greenIdx * redBins + blueIdx * redBins * greenBins;
                histogramData[singleIndex] += 1;
                total += 1;
            }
        }

        // start to normalize the histogram data
        for (int i = 0; i < histogramData.length; i++) {
            histogramData[i] = histogramData[i] / total;
        }

        return histogramData;
    }

    private float getBinIndex(int binCount, int color, int colorMaxValue) {

        float binIndex = (((float) color) / ((float) colorMaxValue)) * ((float) binCount);
        if (binIndex >= binCount)
            binIndex = binCount - 1;
        return binIndex;
    }

    public int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {

        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
            return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
        return image.getRGB(x, y, width, height, pixels, 0, width);
    }

}
