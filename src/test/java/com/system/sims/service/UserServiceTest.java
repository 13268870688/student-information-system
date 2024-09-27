package com.system.sims.service;

import com.system.sims.SimsApplication;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SimsApplication.class)
class UserServiceTest {
    @Resource
    private UserService service;

    @Test
    void userLogin() {
        service.UserLogin("admin123","123456");
    }
}