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

public class ImageComparer {

    private BufferedImage sourceImage;

    private BufferedImage candidateImage;

    public ImageComparer(BufferedImage srcImage, BufferedImage canImage) {

        this.sourceImage = srcImage;
        this.candidateImage = canImage;
    }

    /**
     * Bhattacharyya Coefficient
     * http://www.cse.yorku.ca/~kosta/CompVis_Notes/bhattacharyya.pdf
     *
     * @return
     */
    public double modelMatch() {

        HistogramFilter hfilter = new HistogramFilter();
        float[] sourceData = hfilter.filter(sourceImage);
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
        return similarity;
    }


}
