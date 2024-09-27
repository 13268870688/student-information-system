package com.system.sims.service;

import com.system.sims.beans.Course;

import java.util.Date;
import java.util.List;


public interface CourseService {
    Course addCourse(String cname, int credit, String tno, Date test_time, Date begin_time, int class_hour, String class_location);

    List<Course> getOptionalCourse();
    public void deleteCourse(int courseId);

    Course getCourseById(int courseId);

    boolean updateCourse(int courseId,String cname, int credit, String tno, Date test_time, Date begin_time, int class_hour, String class_location);
}
