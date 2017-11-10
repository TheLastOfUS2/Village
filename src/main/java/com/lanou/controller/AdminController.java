package com.lanou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dllo on 17/11/9.
 */
@Controller
public class AdminController {

    @RequestMapping(value = "/adminList")
    public String adminList(){
        return "admin-list";
    }
}
