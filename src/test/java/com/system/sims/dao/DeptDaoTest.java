package com.system.sims.dao;

import com.system.sims.SimsApplication;
import com.system.sims.beans.Dept;
import jakarta.annotation.Resource;
import jakarta.annotation.security.RunAs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = SimsApplication.class)
class DeptDaoTest {
    @Resource
    private DeptDao dao;


}