package com.system.sims.controller;

import com.system.sims.beans.*;
import com.system.sims.service.TeacherService;
import com.system.sims.utils.PermissionControlUtils;
import com.system.sims.utils.pageUtils;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService service;

    @RequestMapping("/all")
    public String SearchTeacher(
            int pageNum,
            int pageSize,
            @RequestParam(defaultValue = "",required = false) String key,
            @RequestParam(defaultValue = "",required = false) String value,
            @RequestParam(required = false) String[] name,
            @RequestParam(required = false) String[] min,
            @RequestParam(required = false) String[] max,
            Model model){
        if(PermissionControlUtils.limitStudent()) {
            pageUtils students;
            if(name == null){
                students = service.searchTeacher(pageNum, pageSize,key,value);
            }
            else{
                students = service.searchTeacher(pageNum, pageSize,name,min,max);
            }
            model.addAttribute("pageInf", students);
            return "tables-teacher";
        }
        else {
            return "404Page";
        }
    }

    @RequestMapping("/course")
    public String SearchTeacherCourse(
            int pageNum,
            int pageSize,
            @RequestParam(defaultValue = "",required = false) String key,
            @RequestParam(defaultValue = "",required = false) String value,
            @RequestParam(required = false) String[] name,
            @RequestParam(required = false) String[] min,
            @RequestParam(required = false) String[] max,
            Model model){
        if(PermissionControlUtils.limitStudent()) {
            pageUtils students;
            if(name == null){
                students = service.searchTeacherCourse(pageNum, pageSize,key,value);
            }
            else{
                students = service.searchTeacherCourse(pageNum, pageSize,name,min,max);
            }
            model.addAttribute("pageInf", students);
            return "tables-teacher";
        }
        else {
            return "404Page";
        }
    }

    @RequestMapping("/add")
    @ResponseBody
    public ajaxResponse addTeacher(String tname,
                                   String ssex,
                                   String dno,
                                   String study,
                                   String direction,
                                   @DateTimeFormat(pattern = "yyyy-MM-dd") String birth,
                                   String job)
    {
        try{
            if(PermissionControlUtils.limitTeacherAndStudent()){
                Teacher teacher = service.addTeacher(tname, ssex, dno, study, direction, birth, job);
                if(teacher == null){
                    return new ajaxResponse(500,null,"服务器出现错误，请等一下重新尝试");
                }
                else{
                    return new ajaxResponse(200,teacher,"添加成功");
                }
            }
            else{
                return new ajaxResponse(401,null,"用户权限不足，需要管理员权限");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }


    @RequestMapping("/ajaxAll")
    @ResponseBody
    public List<Teacher> getAllDept(){
        if (PermissionControlUtils.limitTeacherAndStudent()){
            pageUtils pageUtils = service.searchTeacher(1, 1000, "", "");
            return (List<Teacher>)pageUtils.getData();
        }else {
            return null;
        }
    }

    @RequestMapping("/find_release_course")
    @ResponseBody
    public ajaxResponse FindMyReleaseCourse(){
        try{
            if (PermissionControlUtils.limitAdmin() && PermissionControlUtils.limitStudent()){
                List<Course> courses = service.FindMyReleaseCourse();
                return new ajaxResponse(200,courses,"查询成功");
            }
            else {
                return new ajaxResponse(402,null,"查询失败,需要老师权限");
            }
        } catch (Exception e) {
            return new ajaxResponse(400,null,"查询失败");
        }
    }

    @RequestMapping("/find_select_student")
    @ResponseBody
    public ajaxResponse findSelectCourseStudent(int courseId){
        try{
            List<Student> students = service.searchSelectStudent(courseId);
            return new ajaxResponse(200,students,"查询成功");
        } catch (Exception e) {
            return new ajaxResponse(400,null,e.getMessage());
        }
    }

    @RequestMapping("/update_teacher_inf")
    @ResponseBody
    public ajaxResponse updateTeacherInf(String tname,
                                   String tno,
                                   String ssex,
                                   String dno,
                                   String study,
                                   String direction,
                                   @DateTimeFormat(pattern = "yyyy-MM-dd") String birth,
                                   String job)
    {
        try{
            if(PermissionControlUtils.limitTeacherAndStudent()){
                Teacher teacher = new Teacher(tno,tname,ssex,dno,study,direction,birth,job);
                service.updateTeacherInf(teacher);
                return new ajaxResponse(200,null,"修改成功");
            }
            else{
                return new ajaxResponse(401,null,"用户权限不足，需要管理员权限");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }

    @RequestMapping("/delete_teacher")
    @ResponseBody
    public ajaxResponse deleteTeacher(String tno){
        try {
            if(PermissionControlUtils.limitTeacherAndStudent()){
                service.deleteTeacher(tno);
                return new ajaxResponse(200,null,"删除成功");
            }
            else{
                return new ajaxResponse(404,null,"没有相关权限");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }


    @RequestMapping("/page_update_teacher")
    public String pageUpdateTeacher(String tno,Model model){
        try{
            if(PermissionControlUtils.limitTeacherAndStudent()){
                Teacher teacher = service.searchTeacherById(tno);

                teacher.setSsex(teacher.getSsex().trim());


                model.addAttribute("birth",teacher.getBirthDate().substring(0,10));
                model.addAttribute("data",teacher);
                return "form-admin-updateTeacher";
            }
            else {
                return "401Page";
            }
        } catch (Exception e) {
            return "404Page";
        }
    }


}
