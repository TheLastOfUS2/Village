package com.lanou.controller;

import com.lanou.bean.SysUser;

import com.lanou.exception.CustomException;
import com.lanou.service.SysUserService;
import com.lanou.util.AjaxResult;
import com.lanou.util.VerifyCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by dllo on 2017/11/8.
 */
@Controller
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/index")
    public String frontpage(){
        return "index";
    }

    @RequestMapping(value = "/login")
    public String toLogin(){
        //判断登录状态,如果是登录时候,就直接进入主页,否则去登录页面
        if (SecurityUtils.getSubject().isAuthenticated()){
            return "index";
        }
        return "login";
    }

    @RequestMapping(value = "/loginsubmit")
    public String loginsubmit(HttpServletRequest request) throws Exception {
        //shiro在认证过程中出现错误后,将异常路径通过request返回

        String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");

        if (exceptionClassName.equals(UnknownAccountException.class.getName())){
            throw new CustomException("账户名不存在");
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)){
            throw new CustomException("密码错误");
        } else {
            throw new Exception();
        }
    }

    @ResponseBody
    @RequestMapping("/codeImg")
    public void getCodeImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //用于处理真正的返回结果
        VerifyCode verifyCode = new VerifyCode();//创建工具类对象

        BufferedImage image = verifyCode.getImage();//验证码工具生成图片对象

        //将验证码保存到session中
        request.getSession().setAttribute("verifyCode",verifyCode.getText());


        //获得response对象的输出流用于图像的写入
        OutputStream os = response.getOutputStream();

        VerifyCode.output(image,os);//将图片对象映射到输出流中
    }

    @ResponseBody
    @RequestMapping(value = "/getAllUser")
    public AjaxResult getAllUser(){

        return new AjaxResult(sysUserService.getAllUser());

    }

    @ResponseBody
    @RequestMapping(value = "/judgeCode")
    public AjaxResult judgeCode(HttpServletRequest request, @RequestParam("code")String code){
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        if (!(code.equalsIgnoreCase(verifyCode))){
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.setMessage("验证码不正确");
            ajaxResult.setErrorCode(1);
            return ajaxResult;
        }
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setErrorCode(0);
        return ajaxResult;
    }

    @RequestMapping(value = "/welcome")
    public String toWelcome(){
        return "welcome";
    }
}
