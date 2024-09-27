package com.system.sims.service;

import com.system.sims.beans.Course;
import com.system.sims.beans.Student;
import com.system.sims.beans.Teacher;
import com.system.sims.utils.pageUtils;

import java.util.Date;
import java.util.List;

public interface TeacherService {
    pageUtils searchTeacher(int pageNum, int pageSize, String key, String value);
    pageUtils searchTeacher(int pageNum,int pageSize,String[] name,String[] min,String[] max);

    pageUtils searchTeacherCourse(int pageNum, int pageSize, String key, String value);
    pageUtils searchTeacherCourse(int pageNum,int pageSize,String[] name,String[] min,String[] max);


    Teacher addTeacher(String tname, String ssex, String dno, String study, String direction,String birth,String job);

    List<Student> searchSelectStudent(int courseId);

    List<Course> FindMyReleaseCourse();

    Teacher searchTeacherById(String tno);

    void updateTeacherInf(Teacher teacher);

    void deleteTeacher(String tno);


}
