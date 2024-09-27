package com.system.sims.controller;

import com.system.sims.beans.*;
import com.system.sims.service.StudentService;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService service;

    @RequestMapping("/all")
    public String SearchAllStudent(int pageNum,
                                   int pageSize,
                                   @RequestParam(defaultValue = "",required = false) String key,
                                   @RequestParam(defaultValue = "",required = false) String value,
                                   @RequestParam(required = false) String[] name,
                                   @RequestParam(required = false) String[] min,
                                   @RequestParam(required = false) String[] max,
                                   Model model){
        if(PermissionControlUtils.limitStudent()) {
            pageUtils students = null;
            if(key.equals("")&&value.equals("")&&name == null){
                students = service.searchAllStudent(pageNum, pageSize);
            }
            else if(name != null){
                students = service.FuzzySearchStudent(pageNum,pageSize,name,min,max);
            }
            else {
                students = service.FuzzySearchStudent(pageNum, pageSize,key,value);

            }
            model.addAttribute("pageInf", students);
            return "tables-basic";
        }
        else {
            return "404Page";
        }
    }

    @RequestMapping("/course")
    public String SearchStudentCourse(
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
                students = service.searchStudentCourse(pageNum, pageSize,key,value);
            }
            else{
                students = service.searchStudentCourse(pageNum, pageSize,name,min,max);
            }
            model.addAttribute("pageInf", students);
            return "tables-basic";
        }
        else {
            return "404Page";
        }
    }

    @RequestMapping("/grade")
    public String SearchStudentGrade(
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
                students = service.searchStudentGrade(pageNum, pageSize,key,value);
            }
            else{
                students = service.searchStudentGrade(pageNum, pageSize,name,min,max);
            }
            model.addAttribute("pageInf", students);
            return "tables-basic";
        }
        else {
            return "404Page";
        }
    }

    @RequestMapping("/update_student_inf")
    public String updateStudentInf(String sno,Model model){
        try {
            if (PermissionControlUtils.limitTeacherAndStudent()){
                Student studentById = service.getStudentById(sno);
                studentById.setSsex(studentById.getSsex().trim());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(studentById.getBirthDate());

                model.addAttribute("data",studentById);
                model.addAttribute("time",formattedDate);
                return "form-admin-updateStudent";
            }
            else{
                return "404Page";
            }
        } catch (Exception e) {
            return "401Page";
        }

    }

    @RequestMapping("/add")
    @ResponseBody
    public ajaxResponse addStudent(String sname, String ssex, int grade, String sdept,@DateTimeFormat(pattern = "yyyy-MM-dd") Date birth){
        try{
            if(PermissionControlUtils.limitTeacherAndStudent()){
                Student student = service.addStudent(sname, ssex, grade, sdept, birth);
                if(student == null){
                    return new ajaxResponse(500,null,"服务器出现错误，请等一下重新尝试");
                }
                else{
                    return new ajaxResponse(200,student,"添加成功");
                }
            }
            else{
                return new ajaxResponse(401,null,"用户权限不足，需要管理员权限");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }

    @RequestMapping("/take_course")
    @ResponseBody
    public ajaxResponse selectCourse(int courseId){
        try {
            if (PermissionControlUtils.limitTeacher() && PermissionControlUtils.limitAdmin()){
                service.selectCourse(courseId);
                return new ajaxResponse(200,null,"添加成功");
            }
            else {
                return new ajaxResponse(401,null,"只有学生能使用此功能");
            }
        } catch (Exception e) {
            return new ajaxResponse(402,null,e.getMessage());
        }
    }

    @RequestMapping("/find_select_course")
    @ResponseBody
    public ajaxResponse findMySelectCourse(){
        try {
            if(PermissionControlUtils.limitTeacher() && PermissionControlUtils.limitAdmin()){
                List<Course> studentCourses = service.FindMySelectCourse();
                if(studentCourses == null) {
                    return new ajaxResponse(500,null,"服务器错误");
                }
                return new ajaxResponse(200,studentCourses,"成功");
            }
            else{
                return new ajaxResponse(402,null,"访问受限");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }

    }

    @RequestMapping("/leave_course")
    @ResponseBody
    public ajaxResponse leaveCourse(int courseId){
        try {
            if(PermissionControlUtils.limitTeacher() && PermissionControlUtils.limitAdmin()){
                service.leaveCourse(courseId);
                return new ajaxResponse(200,null,"成功");
            }
            else{
                return new ajaxResponse(402,null,"访问受限");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }

    }

    @RequestMapping("/get_grade")
    @ResponseBody
    public ajaxResponse generateExcel(int courseId){
        try {
            List<StudentGrade> studentGrade = service.getStudentGrade(courseId);
            if(studentGrade == null){
                return new ajaxResponse(500,null,"身份权限不足");
            }
            return new ajaxResponse(200,studentGrade,"获取成功");
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }

    @RequestMapping("/get_grade_special")
    @ResponseBody
    public ajaxResponse getGradeSpecial(String sno,int cno){
        if (PermissionControlUtils.limitStudent()) {
            try {
                StudentGrade studentGradeSpecial = service.getStudentGradeSpecial(sno, cno);
                return new ajaxResponse(200,studentGradeSpecial,"获取成功");
            } catch (Exception e) {
                return new ajaxResponse(500,null,e.getMessage());
            }
        }
        else {
            return new ajaxResponse(500,null,"用户权限不足");
        }
    }

    @RequestMapping("/get_student_by_id")
    @ResponseBody
    public ajaxResponse getStudentById(String sno){
        try {
            if (PermissionControlUtils.limitStudent()){
                Student studentById = service.getStudentById(sno);
                return new ajaxResponse(200,studentById,"获取成功");
            }else {
                return new ajaxResponse(500,null,"获取失败");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,"获取失败");
        }
    }

    @RequestMapping("/updateGrade")
    @ResponseBody
    public ajaxResponse updateGrade(String sno,String cno,int testGrade,int generalGrade,int finalGrade){
        try {
            if (PermissionControlUtils.limitStudent()){
                StudentGrade grade = new StudentGrade(sno,null,cno,null,testGrade,generalGrade,finalGrade);
                service.updateStudentGrade(grade);
                return new ajaxResponse(200,null,"更新成功");
            }else {
                return new ajaxResponse(500,null,"权限不足");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }
    @RequestMapping("/ajax_update_student_inf")
    @ResponseBody
    public ajaxResponse updateStudentInf(String sno,String sname, String ssex, int grade, String sdept,@DateTimeFormat(pattern = "yyyy-MM-dd") Date birth){
        try {
            if (PermissionControlUtils.limitTeacherAndStudent()){
                Student student = new Student(sno,sname,ssex,grade,sdept,birth);
                service.updateStudentInf(student);
                return new ajaxResponse(200,null,"更新成功");
            }else {
                return new ajaxResponse(500,null,"权限不足");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }

    @RequestMapping("/delete_student_inf")
    @ResponseBody
    public ajaxResponse deleteStudentInf(String sno){
        try {
            if (PermissionControlUtils.limitTeacherAndStudent()){
                service.deleteStudentInf(sno);
                return new ajaxResponse(200,null,"删除成功");
            }else {
                return new ajaxResponse(500,null,"权限不足，需要管理员权限");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }

    @RequestMapping("/delete_student_grade")
    @ResponseBody
    public ajaxResponse deleteStudentGrade(String sno,int cno){
        try {
            if (PermissionControlUtils.limitTeacherAndStudent()){
                service.deleteStudentCourse(sno,cno);
                return new ajaxResponse(200,null,"删除成功");
            }else {
                return new ajaxResponse(500,null,"权限不足，需要管理员权限");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }


}
