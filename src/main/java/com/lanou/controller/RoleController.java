package com.lanou.controller;

import com.lanou.service.RoleService;
import com.lanou.util.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 17/11/9.
 */
@Controller
public class RoleController {

    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/adminRole")
    public String adminRole(){
        return "admin-role";
    }

    @ResponseBody
    @RequestMapping(value = "/getAllRoles")
    public AjaxResult getAllRoles(){

        return new AjaxResult(roleService.getAllRoles());
    }

    @ResponseBody
    @RequestMapping(value = "/delMore")
    public int delMore(@RequestParam("ids")Integer[] ids){

        List<Integer> idz = new ArrayList<>();

        for (Integer id : ids) {
            idz.add(id);
        }



        return roleService.delMore(idz);
    }

}
