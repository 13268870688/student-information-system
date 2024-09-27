package com.system.sims.service;

import com.system.sims.beans.Dept;
import com.system.sims.utils.pageUtils;

import java.util.Date;

public interface DeptService {
    pageUtils searchDept(int pageNum, int pageSize, String key, String value);
    pageUtils searchDept(int pageNum,int pageSize,String[] name,String[] min,String[] max);

    Dept addDept(String inf,String dname);

    Dept getDeptById(String dno);

    int deleteDept(String dno);

    void updateDept(Dept dept);
}
