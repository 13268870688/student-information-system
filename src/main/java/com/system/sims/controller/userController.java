package com.system.sims.controller;

import com.system.sims.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class userController {
    @Resource
    private UserService service;

    @RequestMapping("/login")
    public String UserLogin(String username, String password, Model model){
        try{
            service.UserLogin(username,password);
            return "redirect:/index.html";
        } catch (Exception e) {
            model.addAttribute("errMsg","账号或密码错误");
            return "forward:/login.html";
        }

    }

    @RequestMapping("/login_out")
    public String UserLogin(HttpSession session){
        session.invalidate();
        return "redirect:/login.html";
    }
}
