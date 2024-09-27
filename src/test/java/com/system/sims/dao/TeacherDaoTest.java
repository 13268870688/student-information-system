package com.system.sims.dao;

import com.system.sims.SimsApplication;
import com.system.sims.beans.StudentCourse;
import com.system.sims.beans.Teacher;
import com.system.sims.beans.TeacherCourse;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SimsApplication.class)
class TeacherDaoTest {
    @Resource
    private TeacherDao dao;

    @Test
    void searchTeacher() {

        List<Teacher> teachers = dao.searchTeacher(1, 7, null, null);
        System.out.println(teachers);
    }

    @Test
    void searchTeacherCourse() {
        List<TeacherCourse> studentCourses = dao.searchTeacherCourse(1, 7, null, null);
        System.out.println(studentCourses);
    }

    @Test
    void getTeacherById() {
        Teacher t202311260001 = dao.getTeacherById("t202311260001");
        System.out.println(t202311260001);
    }
}