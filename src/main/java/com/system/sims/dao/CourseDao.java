package com.system.sims.dao;

import com.system.sims.beans.Course;
import com.system.sims.beans.Range;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface CourseDao {

    int updateCourse(Course course);

    Course getCourseById(int id);
    int insertCourse(Course course);

    int deleteCourseById(int id);

    List<Course> getCourseByCondition(String key, String value,int RangeType);

    int deleteCourseByCondition(Map<String,String> keys,Object obj);

    int countCourse();
}
