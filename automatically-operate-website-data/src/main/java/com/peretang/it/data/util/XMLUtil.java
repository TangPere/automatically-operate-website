/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.data.util;

import com.peretang.it.entity.Config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pere
 * @date 2017-07-30
 * Use to XML to Java or Java to XML
 */
public class XMLUtil {

    public static Map<String, Config> getConfigMap(String path) throws JAXBException {
        File file = new File(path);
        Map<String, Config> resultMap = new HashMap<>();
        JAXBContext jc = JAXBContext.newInstance(Config.class);
        Unmarshaller u = jc.createUnmarshaller();
        if (file.isDirectory()) {
            File[] xmlList = file.listFiles((dir, name) -> name.endsWith(".xml"));
            if (xmlList != null) {
                for (File xmlFile : xmlList) {
                    Config config = (Config) u.unmarshal(xmlFile);
                    resultMap.put(config.getName(), config);
                }
            }
        } else {
            Config config = (Config) u.unmarshal(file);
            resultMap.put(config.getName(), config);
        }

        return resultMap;
    }


}
