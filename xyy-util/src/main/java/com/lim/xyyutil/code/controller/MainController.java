package com.lim.xyyutil.code.controller;

import com.lim.xyyutil.annotation.LocalLock;
import com.lim.xyyutil.code.constant.LimConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinhao
 */
@RestController
@RequestMapping(value = {"/main/"})
public class MainController {

    private final LimConstant constant;

    @Autowired
    public MainController(LimConstant limConstant) {
        this.constant = limConstant;
    }

    @LocalLock(key = "main:arg[0]")
    @GetMapping(value = "constant.json")
    public String constant(Model model) {
        String res = "";
        res += constant.getName();
        System.out.println("before-finally");
        return res;
    }
}
