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

public class SimilarImageUtil {

    private float[] sourceData;

    public SimilarImageUtil(String sourceFileName) {

        BufferedImage sourceImage = ImageHelper.readPNGImage(sourceFileName);
        HistogramFilter hfilter = new HistogramFilter();
        this.sourceData = hfilter.filter(sourceImage);

    }

    public boolean isSimilar(String targetFileName) {

        BufferedImage candidateImage = ImageHelper.readPNGImage(targetFileName);
        HistogramFilter hfilter = new HistogramFilter();
        float[] candidateData = hfilter.filter(candidateImage);
        double[] mixedData = new double[sourceData.length];
        for (int i = 0; i < sourceData.length; i++) {
            mixedData[i] = Math.sqrt(sourceData[i] * candidateData[i]);
        }

        // The values of Bhattacharyya Coefficient ranges from 0 to 1,
        double similarity = 0;
        for (double aMixedData : mixedData) {
            similarity += aMixedData;
        }

        // The degree of similarity
        return similarity > 0.7;
    }
}
