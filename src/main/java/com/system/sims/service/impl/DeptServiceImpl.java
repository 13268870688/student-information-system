package com.system.sims.service.impl;

import com.system.sims.beans.*;
import com.system.sims.dao.DeptDao;
import com.system.sims.dao.TagDao;
import com.system.sims.service.DeptService;
import com.system.sims.utils.CommonMap;
import com.system.sims.utils.IdCreator;
import com.system.sims.utils.pageUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptDao dao;
    @Resource
    private TagDao tagDao;

    @Override
    public pageUtils searchDept(int pageNum, int pageSize, String key, String value) {
        key = CommonMap.DeptMapping(key);
        HashMap<String, String> map = CommonMap.getCommonMap("dno","inf","dname");
        if(!key.equals("")){
            map.put(key,value);
        }
        else{
            CommonMap.setAllInMap(map, value);
        }
        List<Dept> data = dao.searchDept(pageNum, pageSize, map,null);
        List<String> list = tagDao.getTableTagName("6");
        List<String> header = new ArrayList<String>();
        header.add("系号");
        header.add("系名");
        header.add("介绍");
        return new pageUtils("3","6",pageNum,data,header,"学校系别信息表",key,list);
    }

    @Override
    public pageUtils searchDept(int pageNum, int pageSize, String[] name, String[] min, String[] max) {
        if(min.length != max.length || min.length != name.length) throw new RuntimeException("长度不匹配");
        try{
            HashMap<String, Range> RangeMap = filterCommon(name, min, max);
            List<Dept> data = dao.searchDept(pageNum, pageSize, null, RangeMap);
            List<String> list = tagDao.getTableTagName("6");
            List<String> header = new ArrayList<String>();
            header.add("系号");
            header.add("系名");
            header.add("介绍");
            return new pageUtils("3","6",pageNum,data,header,"学校系别信息表","",list);

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Dept addDept(String inf, String dname) {
        try{
            String dno;
            String max = dao.getMax();
            if(max == null) dno = "10001";
            else dno = String.valueOf(Integer.parseInt(max) + 1);

            //插入
            Dept dept = new Dept(dno,inf,dname);
            int i = dao.insertDept(dept);

            return dept;
        } catch (Exception e) {
            throw new RuntimeException("添加失败，可能的原因有：数据长度过大或者存在必填参数未填写（必填参数有：系名）");
        }
    }

    @Override
    public Dept getDeptById(String dno) {
        return dao.getDeptById(dno);
    }

    @Override
    public int deleteDept(String dno) {
        return dao.deleteDeptById(dno);
    }

    @Override
    @Transactional
    public void updateDept(Dept dept) {
        try {
            dao.updateDept(dept);
        } catch (Exception e) {
            throw new RuntimeException("更新失败，服务器出现错误");
        }

    }

    private HashMap<String,Range> filterCommon(String[] name, String[] min, String[] max) {
        Integer[] mins = new Integer[name.length];
        Integer[] maxs = new Integer[name.length];

        for (int i = 0; i < name.length; i++) {
            name[i] = CommonMap.DeptMapping(name[i]);

            if(min[i].equals("?")) mins[i] = Integer.MIN_VALUE;
            else mins[i] = Integer.parseInt(min[i]);

            if(max[i].equals("?")) maxs[i] = Integer.MAX_VALUE;
            else maxs[i] = Integer.parseInt(max[i]);
        }
        Range[] ranges = Range.generateRange(mins, maxs);
        return CommonMap.creatRangeArray(name, ranges);
    }
}
