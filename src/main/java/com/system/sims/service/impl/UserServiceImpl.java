package com.system.sims.service.impl;

import com.system.sims.beans.User;
import com.system.sims.dao.UserDao;
import com.system.sims.service.UserService;
import com.system.sims.utils.UserContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao dao;

    @Override
    public void UserLogin(String username, String password) {

        User users = dao.getByUserNumber(username);
        if(users == null) throw new RuntimeException("账号或密码错误");

        String passwords = users.getPasswords();
        String s = DigestUtils.md5DigestAsHex(password.getBytes());

        if(!s.equals(passwords)){
            throw new RuntimeException("账号或密码错误");
        }

        UserContext.setCurrentUser(users);

    }

    @Override
    public void UserLoginOut() {
        UserContext.setCurrentUser(null);
    }
}
