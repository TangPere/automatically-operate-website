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


    public static boolean isSimilar(String sourceFileName, String targetFileName) {

        BufferedImage targetCandidateImage = ImageHelper.readPNGImage(targetFileName);
        BufferedImage sourceCandidateImage = ImageHelper.readPNGImage(sourceFileName);
        return isSimilar(sourceCandidateImage, targetCandidateImage);
    }

    public static boolean isSimilar(BufferedImage sourceCandidateImage, BufferedImage targetCandidateImage) {
        HistogramFilter histogramFilter = new HistogramFilter();
        float[] sourceCandidateData = histogramFilter.filter(sourceCandidateImage);
        float[] targetCandidateData = histogramFilter.filter(targetCandidateImage);
        double[] mixedData = new double[sourceCandidateData.length];
        for (int i = 0; i < sourceCandidateData.length; i++) {
            mixedData[i] = Math.sqrt(sourceCandidateData[i] * targetCandidateData[i]);
        }

        // The values of Bhattacharyya Coefficient ranges from 0 to 1,
        double similarity = 0;
        for (double aMixedData : mixedData) {
            similarity += aMixedData;
        }

        // The degree of similarity
        return similarity > 0.99;
    }
}
