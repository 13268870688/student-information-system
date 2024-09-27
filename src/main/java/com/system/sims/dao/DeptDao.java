package com.system.sims.dao;

import com.system.sims.beans.Dept;
import com.system.sims.beans.Range;
import com.system.sims.beans.Student;
import com.system.sims.beans.Teacher;


import java.util.List;
import java.util.Map;

public interface DeptDao {
    List<Dept> searchDept(int pageNum, int pageSize, Map<String,String> keys, Map<String, Range> rangeMap);

    int updateDept(Dept dept);

    Dept getDeptById(String id);

    int insertDept(Dept dept);
    int deleteDeptById(String id);

    String getMax();

    int countDept();
}
