package com.system.sims.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.system.sims.beans.Student;
import com.system.sims.service.StudentService;
import com.system.sims.utils.PermissionControlUtils;
import com.system.sims.utils.UserContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
public class pageController {

    @Resource
    private StudentService service;

    @RequestMapping("/index.html")
    public String getIndexHtml(Model model){
        Map<String, Integer> map = service.statistic();
        model.addAttribute("data",map);
        return "index";
    }

    @RequestMapping("/")
    public String defaultHtml(){
        return "index";
    }

    @RequestMapping("/login.html")
    public String loginHtml(){
        return "login";
    }

    @RequestMapping("/form-student.html")
    public String formStudent(){
        if(PermissionControlUtils.limitTeacherAndStudent()){
            return "form-student";
        }
        else {
            return "/401Page";
        }
    }

    @RequestMapping("/form-teacher.html")
    public String formTeacher(){
        if(PermissionControlUtils.limitTeacherAndStudent()){
            return "form-teacher";
        }
        else {
            return "/401Page";
        }

    }

    @RequestMapping("/form-dept.html")
    public String formDept(){
        if(PermissionControlUtils.limitTeacherAndStudent()){
            return "form-dept";
        }
        else {
            return "/401Page";
        }

    }

    @RequestMapping("/form-course.html")
    public String formCourse(){
        if(PermissionControlUtils.limitTeacherAndStudent()){
            return "form-course";
        }
        else {
            return "/401Page";
        }

    }

    @RequestMapping("/form-student-course.html")
    public String formStudentCourse(){
        if(PermissionControlUtils.limitTeacher() && PermissionControlUtils.limitAdmin()){
            return "form-student-course";
        }
        else {
            return "/401Page";
        }
    }

    @RequestMapping("/student-select-table.html")
    public String StudentSelectCourse(){
        if(PermissionControlUtils.limitTeacher() && PermissionControlUtils.limitAdmin()){
            return "student-select-table";
        }
        else {
            return "/401Page";
        }
    }

    @RequestMapping("/table-teacher-course.html")
    public String TeacherReleaseCourse(){
        if(PermissionControlUtils.limitStudent() && PermissionControlUtils.limitAdmin()){
            return "table-teacher-course";
        }
        else {
            return "401Page";
        }
    }

    @RequestMapping("/table-teacher-selectStudent.html")
    public String TeacherSelectStudent(){
        if(PermissionControlUtils.limitStudent() && PermissionControlUtils.limitAdmin()){
            return "table-teacher-selectStudent";
        }
        else {
            return "401Page";
        }

    }

    @RequestMapping("/form-teacher-course.html")
    public String FormTeacherCourse(Model model){
        if(PermissionControlUtils.limitStudent() && PermissionControlUtils.limitAdmin()){
            model.addAttribute("father","5");
            model.addAttribute("child","9");
            return "form-teacher-course";
        }
        else {
            return "401Page";
        }

    }

    @RequestMapping("/form-admin-update-course.html")
    public String FormAdminUpdateCourse(Model model){
        if(PermissionControlUtils.limitTeacherAndStudent()){
            model.addAttribute("father","3");
            model.addAttribute("child","5");
            return "form-teacher-course";
        }
        else {
            return "401Page";
        }
    }

    @RequestMapping("/table-student-course.html")
    public String TableTeacherStudentGrade(){
        if(PermissionControlUtils.limitStudent() && PermissionControlUtils.limitAdmin()){
            return "table-teacher-selectStudent-grade";
        }
        else {
            return "401Page";
        }

    }

    @RequestMapping("/form-teacher-grade.html")
    public String FormTeacherUpdateGrade(Model model) {
        if(PermissionControlUtils.limitStudent() && PermissionControlUtils.limitAdmin()){
            model.addAttribute("father","5");
            model.addAttribute("child","9");
            return "form-teacher-grade";
        }
        else {
            return "401Page";
        }

    }

    @RequestMapping("/form-admin-grade.html")
    public String FormAdminUpdateGrade(Model model) {

        if(PermissionControlUtils.limitTeacherAndStudent()){
            model.addAttribute("father","2");
            model.addAttribute("child","3");
            return "form-teacher-grade";
        }
        else {
            return "401Page";
        }

    }

    @RequestMapping("/form-teacher-add-course.html")
    public String formTeacherAddCourse() {
        if(PermissionControlUtils.limitStudent() && PermissionControlUtils.limitAdmin()){
            return "form-teacher-add-course";
        }
        else {
            return "401Page";
        }

    }
}
