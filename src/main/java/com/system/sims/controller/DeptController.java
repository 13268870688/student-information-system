package com.system.sims.controller;

import com.system.sims.beans.Dept;
import com.system.sims.beans.Student;
import com.system.sims.beans.ajaxResponse;
import com.system.sims.service.DeptService;
import com.system.sims.utils.PermissionControlUtils;
import com.system.sims.utils.pageUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService service;

    @RequestMapping("/all")
    public String SearchDept(
            int pageNum,
            int pageSize,
            @RequestParam(defaultValue = "",required = false) String key,
            @RequestParam(defaultValue = "",required = false) String value,
            @RequestParam(required = false) String[] name,
            @RequestParam(required = false) String[] min,
            @RequestParam(required = false) String[] max,
            Model model){
        if(PermissionControlUtils.limitStudent()) {
            pageUtils data;
            if(name == null){
                data = service.searchDept(pageNum, pageSize,key,value);
            }
            else{
                data = service.searchDept(pageNum, pageSize,name,min,max);
            }
            model.addAttribute("pageInf", data);
            return "tables-teacher";
        }
        else {
            return "404Page";
        }
    }

    @RequestMapping("/page-update-dept")
    public String pageUpdateDept(String dno,Model model){
        try {
            if(PermissionControlUtils.limitTeacherAndStudent()){
                Dept deptById = service.getDeptById(dno);
                model.addAttribute("data",deptById);
                return "form-admin-updateDept";
            }
            else {
                return "404Page";
            }

        } catch (Exception e) {
            return "404Page";
        }
    }


    @RequestMapping("/ajaxAll")
    @ResponseBody
    public List<Dept> getAllDept(){
        if (PermissionControlUtils.limitTeacherAndStudent()){
            pageUtils pageUtils = service.searchDept(1, 100, "", "");
            return (List<Dept>)pageUtils.getData();
        }else {
            return null;
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ajaxResponse deleteDept(String dno){
        if (PermissionControlUtils.limitTeacherAndStudent()){
            service.deleteDept(dno);
            return new ajaxResponse(200,null,"删除成功");
        }else {
            return new ajaxResponse(402,null,"用户权限不足，删除失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public ajaxResponse updateDept(String dno,String dname,String inf){
        try {
            if (PermissionControlUtils.limitTeacherAndStudent()){
                Dept dept = new Dept(dno, inf, dname);
                service.updateDept(dept);
                return new ajaxResponse(200,null,"更新成功");
            }else {
                return new ajaxResponse(402,null,"用户权限不足，更新失败");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }

    @RequestMapping("/add")
    @ResponseBody
    public ajaxResponse addDept(String inf,String dname){
        try{
            if(PermissionControlUtils.limitTeacherAndStudent()){
                Dept dept = service.addDept(inf, dname);
                if(dept == null){
                    return new ajaxResponse(500,null,"服务器出现错误，请等一下重新尝试");
                }
                else{
                    return new ajaxResponse(200,dept,"添加成功");
                }
            }
            else{
                return new ajaxResponse(401,null,"用户权限不足，需要管理员权限");
            }
        } catch (Exception e) {
            return new ajaxResponse(500,null,e.getMessage());
        }
    }


}
