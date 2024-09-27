package com.system.sims.utils;

import com.system.sims.beans.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonMap {


    public static HashMap<String,String> getCommonMap(String ...args){
        HashMap<String,String> p = new HashMap<String,String>();
        for (String i:args) {
            p.put(i,"");
        }
        return p;
    }

    public static HashMap<String,String> setAllInMap(HashMap<String,String> map,String val){
        List<String> list = new ArrayList<String>();
        for (String key : map.keySet()) {
            list.add(key);
        }

        for (String s:
             list) {
            map.put(s,val);
        }
        return map;
    }

    public static HashMap<String,Range> creatRangeArray(String[] name, Range[] value){
        HashMap<String,Range> p = new HashMap<String,Range>();
        for (int i = 0; i < name.length; i++) {
            p.put(name[i],value[i]);
        }
        return p;
    }

    //字符映射
    public static String StuMapping(String key){
        if(key.equals("学号")) key="sno";
        else if(key.equals("姓名")) key="sname";
        else if(key.equals("课程号")) key="cno";
        else if(key.equals("课程名")) key="cname";
        else if(key.equals("系号")) key="sdept";
        else if(key.equals("考试成绩")) key="test_grade";
        else if(key.equals("平时成绩")) key="general_grade";
        else if(key.equals("总成绩")) key="final_grade";
        else key ="";
        return key;
    }

    public static String TeacherMapping(String key){
        if(key.equals("职工号")) key="tno";
        else if(key.equals("姓名")) key="tname";
        else if(key.equals("性别")) key="ssex";
        else if(key.equals("系号")) key="dno";
        else if(key.equals("研究方向")) key="study";
        else if(key.equals("教学方向")) key="direction";
        else if(key.equals("出生日期")) key="birth_date";
        else if(key.equals("职位")) key="job";
        else key ="";
        return key;
    }

    public static String DeptMapping(String key){
        if(key.equals("系号")) key="dno";
        else if(key.equals("系名")) key="dname";
        else if(key.equals("介绍")) key="inf";
        else key ="";
        return key;
    }
}
