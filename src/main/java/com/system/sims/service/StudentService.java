package com.system.sims.service;

import com.system.sims.beans.*;
import com.system.sims.utils.pageUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StudentService {
    pageUtils searchAllStudent(int pageNum, int pageSize);

    pageUtils FuzzySearchStudent(int pageNum,int pageSize,String key,String value);
    pageUtils FuzzySearchStudent(int pageNum,int pageSize,String[] name,String[] min,String[] max);

    pageUtils searchStudentCourse(int pageNum,int pageSize,String key,String value);
    pageUtils searchStudentCourse(int pageNum,int pageSize,String[] name,String[] min,String[] max);

    pageUtils searchStudentGrade(int pageNum,int pageSize,String key,String value);
    pageUtils searchStudentGrade(int pageNum,int pageSize,String[] name,String[] min,String[] max);

    Student addStudent(String sname, String ssex, int grade, String sdept, Date date);

    void selectCourse(int courseId);

    List<Course> FindMySelectCourse();

    void leaveCourse(int courseId);

    Student getStudentById(String sno);

    List<StudentGrade> getStudentGrade(int courseId);

    StudentGrade getStudentGradeSpecial(String sno,int cno);

    void updateStudentGrade(StudentGrade grade);

    void updateStudentInf(Student student);

    void deleteStudentInf(String sno);

    void deleteStudentCourse(String sno,int cno);

    Map<String,Integer> statistic();
}
