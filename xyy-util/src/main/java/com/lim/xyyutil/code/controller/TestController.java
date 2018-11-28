package com.lim.xyyutil.code.controller;

import com.lim.xyyutil.code.constant.LimConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinhao
 */
@RestController
@RequestMapping(value = {"/test/"})
public class TestController {

    private final LimConstant constant;

    @Autowired
    public TestController(LimConstant limConstant) {
        this.constant = limConstant;
    }

    @GetMapping(value = "constant.json")
    public String constant() {
        String res = "";
        res += constant.getName();
        return res;
    }
}
