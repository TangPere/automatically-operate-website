/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.operate.singleton;

import java.util.Properties;

/**
 * Created by sro on 2017/7/25.
 */
public class PorpSingleton {

    private PorpSingleton() {
        if (SingletonProperties.porp != null) {
            throw new RuntimeException();
        }
    }

    public static Properties getProperties() {
        return SingletonProperties.porp;
    }


    private static class SingletonProperties {
        private static final Properties porp = new Properties();
    }
}
