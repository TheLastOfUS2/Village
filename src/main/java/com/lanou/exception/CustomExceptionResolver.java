package com.lanou.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dllo on 2017/11/7.
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //输出异常信息
        e.printStackTrace();

        CustomException exception =null;
        if (e instanceof CustomException){
            //shiro的异常
            exception = (CustomException) e;
        }else{
            //其他异常
            exception = new CustomException("未知错误");
        }

        //获取异常信息
        String msg = exception.getMessage();
        httpServletRequest.setAttribute("msg",msg);
        try {
            httpServletRequest.getRequestDispatcher("/WEB-INF/error.jsp").forward(httpServletRequest,httpServletResponse);
        } catch (ServletException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return new ModelAndView();
    }
}
