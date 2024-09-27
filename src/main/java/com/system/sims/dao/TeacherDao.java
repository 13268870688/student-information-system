package com.system.sims.dao;

import com.system.sims.beans.*;

import java.util.List;
import java.util.Map;

public interface TeacherDao {
    List<Teacher> searchTeacher(int pageNum, int pageSize, Map<String,String> keys, Map<String, Range> rangeMap);

    List<TeacherCourse> searchTeacherCourse(int pageNum, int pageSize, Map<String,String> keys, Map<String, Range> rangeMap);

    int updateTeacher(Teacher teacher);

    Teacher getTeacherById(String id);

    int insertTeacher(Teacher teacher);
    int deleteTeacherById(String id);

    int countTeacher();
}
