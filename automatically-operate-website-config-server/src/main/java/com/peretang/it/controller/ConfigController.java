/*
 * COPYRIGHT. Pere Tang 2017. ALL RIGHTS RESERVED.
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer language
 * in any way or for any other purposes whatsoever without the prior written
 * consent of Pere Tang.
 */
package com.peretang.it.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Pere H F DENG
 * @date 2017/8/4.
 */
@RestController("/config")
public class ConfigController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

    @RequestMapping(value = "/configList", method = RequestMethod.GET)
    public List<String> getConfigNameList() {

        return null;
    }
}
