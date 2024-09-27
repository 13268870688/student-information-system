package com.system.sims.utils;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;



public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(UserContext.getCurrentUser() == null){
            response.sendRedirect(request.getContextPath() +"/" + "login.html");
            return false;
        }
        return true;
    }
}
