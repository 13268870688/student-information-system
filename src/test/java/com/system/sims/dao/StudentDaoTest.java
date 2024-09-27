package com.system.sims.dao;

import com.system.sims.SimsApplication;
import com.system.sims.beans.Range;
import com.system.sims.beans.Student;
import com.system.sims.beans.StudentCourse;
import com.system.sims.beans.StudentGrade;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SimsApplication.class)
class StudentDaoTest {
    @Resource
    private StudentDao dao;
    @Test
    void getAllByGroup() {
        List<Student> allByGroup = dao.getAllByGroup(10, 5);
        System.out.println(allByGroup);
    }

    @Test
    void fuzzySearch() {
        Map<String, String> map = new HashMap<>();
        map.put("sname","");
        map.put("sno","");
        map.put("sdept","");
        map.put("ssex","");
        map.put("birth_date","");
        map.put("adminsion_grade","");

        Map<String, Range> map2 = new HashMap<>();
        Range range = new Range(Integer.MIN_VALUE,10005);
        map2.put("sdept",range);


        List<Student> students = dao.FuzzySearch(1, 5, null,map2);
        System.out.println(students);
    }

    @Test
    void searchStudentCourse() {

        Map<String, Range> map2 = new HashMap<>();
        Range range = new Range(Integer.MIN_VALUE,10005);
        map2.put("sdept",range);
        List<StudentCourse> students = dao.searchStudentCourse(1, 5, null,map2);
        System.out.println(students);
    }


    @Test
    void searchStudentGrade() {
        Map<String, String> map = new HashMap<>();
        map.put("cname","");
        map.put("cno","");
        map.put("sname","");
        map.put("sno","");
        map.put("test_grade","");
        map.put("final_grade","");
        map.put("general_grade","");

        List<StudentGrade> students = dao.searchStudentGrade(1, 5, map,null);
        System.out.println(students);
    }

    @Test
    void deleteStudentCourseByCondition() {
        Map<String, String> map = new HashMap<>();
        map.put("tno","t202312020001");
        int i = dao.deleteStudentCourseByCondition(map,null);

    }
}