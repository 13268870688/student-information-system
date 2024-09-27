package com.system.sims.service.impl;

import com.system.sims.beans.*;
import com.system.sims.dao.*;
import com.system.sims.service.TeacherService;
import com.system.sims.utils.CommonMap;
import com.system.sims.utils.IdCreator;
import com.system.sims.utils.UserContext;
import com.system.sims.utils.pageUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherDao dao;
    @Resource
    private TagDao tagDao;
    @Resource
    private DeptDao deptDao;
    @Resource
    private IdDao idDao;
    @Resource
    private CourseDao courseDao;
    @Resource
    private UserDao userDao;
    @Resource
    private StudentDao studentDao;
    @Override
    public pageUtils searchTeacher(int pageNum, int pageSize, String key, String value) {
        key = CommonMap.TeacherMapping(key);
        HashMap<String, String> map = CommonMap.getCommonMap("tno","tname","ssex","dno","study","direction","birth_date","job");
        if(!key.equals("")){
            map.put(key,value);
        }
        else{
            CommonMap.setAllInMap(map, value);
        }
        List<Teacher> data = dao.searchTeacher(pageNum, pageSize, map,null);
        List<String> list = tagDao.getTableTagName("4");
        List<String> header = new ArrayList<String>();
        header.add("职工号");
        header.add("姓名");
        header.add("系号");
        return new pageUtils("3","4",pageNum,data,header,"全体教师信息表",key,list);

    }

    @Override
    public pageUtils searchTeacher(int pageNum, int pageSize, String[] name, String[] min, String[] max) {
        if(min.length != max.length || min.length != name.length) throw new RuntimeException("长度不匹配");
        try{
            HashMap<String, Range> RangeMap = filterCommon(name, min, max);
            List<Teacher> data = dao.searchTeacher(pageNum, pageSize, null, RangeMap);
            List<String> list = tagDao.getTableTagName("4");
            List<String> header = new ArrayList<String>();
            header.add("职工号");
            header.add("姓名");
            header.add("系号");
            return new pageUtils("3","4",pageNum,data,header,"全体教师信息表","",list);

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public pageUtils searchTeacherCourse(int pageNum, int pageSize, String key, String value) {
        key = CommonMap.TeacherMapping(key);
        HashMap<String, String> map = CommonMap.getCommonMap("tno","tname","cno","cname","dno");
        if(!key.equals("")){
            map.put(key,value);
        }
        else{
            CommonMap.setAllInMap(map, value);
        }
        List<TeacherCourse> data = dao.searchTeacherCourse(pageNum, pageSize, map,null);
        List<String> list = tagDao.getTableTagName("5");
        List<String> header = new ArrayList<String>();
        header.add("职工号");
        header.add("姓名");
        header.add("课程号");
        header.add("课程名");
        header.add("系号");
        return new pageUtils("3","5",pageNum,data,header,"教师课程信息表",key,list);
    }

    @Override
    public pageUtils searchTeacherCourse(int pageNum, int pageSize, String[] name, String[] min, String[] max) {
        if(min.length != max.length || min.length != name.length) throw new RuntimeException("长度不匹配");
        try{
            HashMap<String, Range> RangeMap = filterCommon(name, min, max);
            List<Teacher> data = dao.searchTeacher(pageNum, pageSize, null, RangeMap);
            List<String> list = tagDao.getTableTagName("5");
            List<String> header = new ArrayList<String>();
            header.add("职工号");
            header.add("姓名");
            header.add("课程号");
            header.add("课程名");
            header.add("系号");
            return new pageUtils("3","5",pageNum,data,header,"教师课程信息表","",list);

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    @Transactional
    public Teacher addTeacher(String tname, String ssex, String dno, String study, String direction, String birth, String job) {
        try{
            boolean b = checkInf(tname, ssex, dno, study, direction, birth, job);
            if(!b) throw new RuntimeException("输入的参数错误");
            //获取当天长度
            Id id = idDao.getId();
            //获取日期
            int ud = Calendar.getInstance().get(Calendar.DATE);

            //比较判断是否需要更新长度
            if(id.getDay() != ud){
                idDao.setDay(ud);
                id = new Id(0,0,0);
                idDao.setId(id);

            }

            //生成id号
            int length = id.getTeacherLength();
            String tno = IdCreator.create(length,4);
            tno = "t"+tno;


            //插入
            Teacher tea = new Teacher(tno,tname,ssex,dno,study,direction,birth,job);
            int i = dao.insertTeacher(tea);

            //返回结果
            if(i == 0){
                return null;
            }
            else{
                idDao.setId(new Id(id.getStuLength(),id.getTeacherLength()+1,0));
            }
            return tea;
        } catch (Exception e) {
            throw new RuntimeException("添加失败，可能的原因有：数据长度过大或者存在必填参数未填写（必填参数有：姓名）");
        }
    }

    @Override
    public List<Student> searchSelectStudent(int courseId) {
        Teacher teacherById = dao.getTeacherById(UserContext.getCurrentUser().getUserNumber());
        if(teacherById == null){
            throw new RuntimeException("没有对应的权限");
        }

        return studentDao.searchStudentByCourseId(courseId);
    }

    @Override
    public List<Course> FindMyReleaseCourse() {
        return courseDao.getCourseByCondition("tno", UserContext.getCurrentUser().getUserNumber(), Range.EQUAL);
    }

    @Override
    public Teacher searchTeacherById(String tno) {
        try{
            return dao.getTeacherById(tno);
        } catch (Exception e) {
            throw new RuntimeException("服务器出现错误");
        }

    }

    @Override
    @Transactional
    public void updateTeacherInf(Teacher teacher) {
        try{
            dao.updateTeacher(teacher);
        } catch (Exception e) {
            throw new RuntimeException("服务器出现错误");
        }

    }

    @Override
    @Transactional
    public void deleteTeacher(String tno) {
        try {
            /* 1. 删除所有选择课程的学生选课  */
            /* 1.1. 查找所有开放课程 */
            Map<String, String> map = new HashMap<>();
            List<Course> courses = courseDao.getCourseByCondition("tno", tno, Range.EQUAL);
            for (Course c:
                 courses) {
                map.put("cno",String.valueOf(c.getCno()));
            }
            /* 2. 删除该教师发布的所有课程  */
            map.clear();
            map.put("tno",tno);
            courseDao.deleteCourseByCondition(map,null);
            /* 3. 删除该教师  */
            dao.deleteTeacherById(tno);
            /* 4. 删除user表中的信息 */
            userDao.deleteUserById(tno);
        } catch (Exception e) {
            throw new RuntimeException("服务器出现错误");
        }

    }

    private HashMap<String,Range> filterCommon(String[] name, String[] min, String[] max) {
        Integer[] mins = new Integer[name.length];
        Integer[] maxs = new Integer[name.length];

        for (int i = 0; i < name.length; i++) {
            name[i] = CommonMap.TeacherMapping(name[i]);

            if(min[i].equals("?")) mins[i] = Integer.MIN_VALUE;
            else mins[i] = Integer.parseInt(min[i]);

            if(max[i].equals("?")) maxs[i] = Integer.MAX_VALUE;
            else maxs[i] = Integer.parseInt(max[i]);
        }
        Range[] ranges = Range.generateRange(mins, maxs);
        return CommonMap.creatRangeArray(name, ranges);
    }



    public boolean checkInf(String tname, String ssex, String dno, String study, String direction, String birth, String job){
        if(!ssex.equals("男") && !ssex.equals("女")){
            return false;
        }
        else if(deptDao.getDeptById(dno) == null){
            return false;
        }
        else if(!job.equals("100") && !job.equals("101") && !job.equals("102")){
            return false;
        }
        else return true;
    }
}
