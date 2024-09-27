package com.system.sims.service;

import org.springframework.ui.Model;

public interface UserService {
    public void UserLogin(String username,String password);
    public void UserLoginOut();

}
