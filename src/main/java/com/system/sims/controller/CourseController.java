package com.system.sims.controller;

import com.system.sims.beans.Course;
import com.system.sims.beans.Student;
import com.system.sims.beans.ajaxResponse;
import com.system.sims.dao.CourseDao;
import com.system.sims.service.CourseService;
import com.system.sims.utils.PermissionControlUtils;
import com.system.sims.utils.UserContext;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService service;

    @RequestMapping("/add")
    @ResponseBody
    public ajaxResponse addCourse(String cname,
                                  int credit,
                                  @RequestParam(required = false,defaultValue = "null") String tno,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date test_time,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin_time,
                                  int class_hour,
                                  String class_location){
        try{
            if(PermissionControlUtils.limitStudent()){
                if(tno.equals("null") && PermissionControlUtils.limitAdmin()){
                    tno = UserContext.getCurrentUser().getUserNumber();
                }
                Course data = service.addCourse(cname, credit, tno, test_time, begin_time, class_hour, class_location);
                if(data == null){
                    return new ajaxResponse(500,null,"服务器出现错误，请等一下重新尝试");
                }
                else{
                    return new ajaxResponse(200,data,"添加成功");
                }
            }
            else{
                return new ajaxResponse(401,null,"用户权限不足");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }

    @RequestMapping("/get_optional_course")
    @ResponseBody
    public ajaxResponse getOptionalCourse(){
        try{
            List<Course> optionalCourse = service.getOptionalCourse();
            return new ajaxResponse(200,optionalCourse,"添加成功");
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ajaxResponse deleteCourse(int courseId){
        try{
            service.deleteCourse(courseId);
            return new ajaxResponse(200,null,"删除成功");
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }

    @RequestMapping("/get_by_id")
    @ResponseBody
    public ajaxResponse getCourseById(int courseId){
        Course courseById = service.getCourseById(courseId);
        if(courseById == null) return new ajaxResponse(404,null,"没有找到相关资源");
        else{
            return new ajaxResponse(200,courseById,"获取成功");
        }
    }


    @RequestMapping("/update")
    @ResponseBody
    public ajaxResponse updateCourse(
            int courseId,
            String cname,
            int credit,
            String tno,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date test_time,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin_time,
            int class_hour,
            String class_location){

        boolean b = service.updateCourse(courseId, cname, credit, tno, test_time, begin_time, class_hour, class_location);
        if(b){
            return new ajaxResponse(200,null,"更新成功");
        }
        else{
            return new ajaxResponse(500,null,"更新失败");
        }
    }
}
