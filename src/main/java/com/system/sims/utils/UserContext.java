package com.system.sims.utils;



import com.system.sims.beans.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class UserContext {

    private UserContext(){};

    public static final String USER_IN_SESSION="USER_IN_SESSION";

    public static void setCurrentUser(User emp){
        getSession().setAttribute("USER_IN_SESSION", emp);

    }

    public static User getCurrentUser(){
        return (User) getSession().getAttribute(USER_IN_SESSION);

    }


    public static HttpSession getSession(){
        ServletRequestAttributes attrs=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest().getSession();
    }
}
