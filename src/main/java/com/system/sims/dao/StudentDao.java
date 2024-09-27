package com.system.sims.dao;

import com.system.sims.beans.*;

import java.util.List;
import java.util.Map;

public interface StudentDao {
    List<Student> getAllByGroup(int pageNum,int pageSize);
    List<Student> FuzzySearch(int pageNum, int pageSize, Map<String,String> keys,Map<String, Range> rangeMap);

    List<StudentCourse> searchStudentCourse(int pageNum,int pageSize,Map<String,String> keys,Map<String, Range> rangeMap);

    List<StudentGrade> searchStudentGrade(int pageNum,int pageSize,Map<String,String> keys,Map<String, Range> rangeMap);

    int updateStudent(Student student);

    Student getStudentById(String id);

    int insertStudent(Student student);
    int deleteStudentById(String id);

    int insertStudentCourse(String sno,int courseId);

    StudentGrade getStudentGrade(String sno,int cno);
    int updateStudentCourse(StudentGrade grade);
    int deleteStudentCourse(String sno,int courseId);

    List<Student> searchStudentByCourseId(int courseId);

    /*由于mybatis的对象解析机制，参数不能只有map对象，只有map对象无法使用foreach*/
    int deleteStudentCourseByCondition(Map<String,String> keys,Object obj);

    int countStudent();
}
