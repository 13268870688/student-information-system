package com.system.sims.service;

import com.system.sims.SimsApplication;
import com.system.sims.beans.ajaxResponse;
import com.system.sims.dao.StudentDao;
import com.system.sims.utils.pageUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SimsApplication.class)
class StudentServiceTest {
    @Resource
    private StudentService service;

    @Test
    void fuzzySearchStudent() {
        pageUtils pageUtils = service.FuzzySearchStudent(1, 7, "姓名", "朱");
        System.out.println(pageUtils.getData());
    }

    @Test
    void fuzzySearchStudent2() {
        String[] test = new String[1];
        test[0] = "系号";
        String[] min = new String[1];
        min[0]="";
        String[] max = new String[1];
        max[0]="10005";
        pageUtils pageUtils = service.FuzzySearchStudent(1, 7, test, min, max);
        System.out.println(pageUtils.getData());
    }

    @Test
    void addStudent() {

    }


}