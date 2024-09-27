package com.system.sims.service.impl;

import com.system.sims.beans.Course;
import com.system.sims.beans.Range;
import com.system.sims.beans.StudentCourse;
import com.system.sims.beans.Teacher;
import com.system.sims.dao.CourseDao;
import com.system.sims.dao.StudentDao;
import com.system.sims.dao.TeacherDao;
import com.system.sims.service.CourseService;
import com.system.sims.utils.CommonMap;
import com.system.sims.utils.UserContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseDao dao;

    @Resource
    private TeacherDao teacherDao;

    @Resource
    private StudentDao studentDao;

    @Override
    @Transactional
    public Course addCourse(String cname, int credit, String tno, Date test_time, Date begin_time, int class_hour, String class_location) {
        try{
            boolean b = checkInf(cname, credit, tno, test_time, begin_time, class_hour, class_location);
            if(!b) throw new RuntimeException("输入不符合规定");

            //插入
            Course course = new Course(0,cname,credit,tno,test_time,begin_time,class_hour,class_location);
            int i = dao.insertCourse(course);

            //返回结果
            if(i == 0){
                return null;
            }
            return course;
        } catch (Exception e) {
            throw new RuntimeException("添加失败，可能的原因有：数据长度过大或者存在必填参数未填写（必填参数有：课程名，学分，授课老师）");
        }
    }

    @Override
    public List<Course> getOptionalCourse() {
        try{
            Calendar calendar= Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = dateFormat.format(calendar.getTime());

            List<Course> courses = dao.getCourseByCondition("begin_time", format, Range.GREATER_THAN);
            for (Course c:
                    courses) {
                c.setTno(teacherDao.getTeacherById(c.getTno()).getTname());
            }
            return courses;
        } catch (Exception e) {
            throw new RuntimeException("服务器出现错误");
        }

    }

    @Override
    @Transactional
    public void deleteCourse(int courseId) {
        try {
            if(UserContext.getCurrentUser().getIdentitys().equals("TEACHER")){
                String userNumber = UserContext.getCurrentUser().getUserNumber();
                Course courseById = dao.getCourseById(courseId);
                if(!courseById.getTno().equals(userNumber)){
                    throw new RuntimeException("没有相关权限");
                }
            }

            if(UserContext.getCurrentUser().getIdentitys().equals("STUDENT")){
                throw new RuntimeException("没有相关权限");
            }

            HashMap<String, String> map = CommonMap.getCommonMap("sno", "sname", "cno", "cname", "sdept");
            map.put("cno",String.valueOf(courseId));
            List<StudentCourse> studentCourses = studentDao.searchStudentCourse(1, Integer.MAX_VALUE, map, null);
            for (StudentCourse sc:
                    studentCourses) {
                studentDao.deleteStudentCourse(sc.getSno(),courseId);
            }

            dao.deleteCourseById(courseId);

        } catch (Exception e) {
            throw new RuntimeException("没有相关权限");
        }

    }

    @Override
    public Course getCourseById(int courseId) {
        if(UserContext.getCurrentUser().getIdentitys().equals("ADMIN")){
            return dao.getCourseById(courseId);
        }
        else{
            Teacher teacherById = teacherDao.getTeacherById(UserContext.getCurrentUser().getUserNumber());
            if(teacherById == null) return null;

            Course course = dao.getCourseById(courseId);
            if (course != null && course.getTno().equals(teacherById.getTno())){
                return course;
            }
            return null;
        }
    }

    @Override
    @Transactional
    public boolean updateCourse(int courseId,String cname, int credit, String tno, Date test_time, Date begin_time, int class_hour, String class_location) {
        Course course = new Course(courseId,cname,credit,tno,test_time,begin_time,class_hour,class_location);
        if(UserContext.getCurrentUser().getIdentitys().equals("ADMIN")){
            dao.updateCourse(course);
            return true;
        }
        else{
            Course courseById = dao.getCourseById(courseId);
            if(!courseById.getTno().equals(tno) || !courseById.getTno().equals(UserContext.getCurrentUser().getUserNumber())){
                return false;
            }
            dao.updateCourse(course);

            return true;
        }
    }

    public boolean checkInf(String cname, int credit, String tno, Date test_time, Date begin_time, int class_hour, String class_location){

        //老师不存在或者更改非自己发布课程且不是管理员
        if(teacherDao.getTeacherById(tno) == null || (!UserContext.getCurrentUser().getUserNumber().equals(tno) && !UserContext.getCurrentUser().getIdentitys().equals("ADMIN"))){
            return false;
        }
        return true;
    }



}
