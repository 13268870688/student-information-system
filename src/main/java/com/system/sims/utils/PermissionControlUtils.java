package com.system.sims.utils;

import com.system.sims.beans.Student;

public class PermissionControlUtils {
    public static boolean limitStudent(){
        return !UserContext.getCurrentUser().getIdentitys().equals("STUDENT");
    }
    public static boolean limitTeacher(){
        return !UserContext.getCurrentUser().getIdentitys().equals("TEACHER");
    }
    public static boolean limitAdmin(){
        return !UserContext.getCurrentUser().getIdentitys().equals("ADMIN");
    }
    public static boolean limitTeacherAndStudent(){
        return limitStudent() && limitTeacher();
    }

}
