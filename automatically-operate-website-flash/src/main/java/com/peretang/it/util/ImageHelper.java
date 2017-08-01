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


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageHelper {


    public static BufferedImage readPNGImage(String filename) {

        try {
            File inputFile = new File(filename);
            return ImageIO.read(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
