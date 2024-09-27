package com.system.sims.service.impl;

import com.system.sims.beans.*;
import com.system.sims.dao.*;
import com.system.sims.service.StudentService;
import com.system.sims.utils.CommonMap;
import com.system.sims.utils.IdCreator;
import com.system.sims.utils.UserContext;
import com.system.sims.utils.pageUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentDao dao;
    @Resource
    private TagDao tagDao;
    @Resource
    private IdDao idDao;
    @Resource
    private DeptDao deptDao;

    @Resource
    private UserDao userDao;
    @Resource
    private CourseDao courseDao;
    @Resource
    private TeacherDao teacherDao;

    @Override
    public pageUtils searchAllStudent(int pageNum, int pageSize) {


        List<Student> data = dao.getAllByGroup(pageNum, pageSize);
        List<String> list = tagDao.getTableTagName("1");
        List<String> header = new ArrayList<String>();
        header.add("学号");
        header.add("姓名");
        header.add("系号");
        return new pageUtils("2","1",pageNum,data,header,"全体学生信息表","",list);
    }

    @Override
    public pageUtils FuzzySearchStudent(int pageNum, int pageSize,String key,String value) {
        key = CommonMap.StuMapping(key);
        HashMap<String, String> map = CommonMap.getCommonMap("sname", "sno", "sdept", "ssex", "birth_date", "adminsion_grade");
        if(!key.equals("")){
            map.put(key,value);
        }
        else{
            CommonMap.setAllInMap(map, value);
        }
        List<Student> data = dao.FuzzySearch(pageNum, pageSize, map,null);
        List<String> list = tagDao.getTableTagName("1");
        List<String> header = new ArrayList<String>();
        header.add("学号");
        header.add("姓名");
        header.add("系号");
        return new pageUtils("2","1",pageNum,data,header,"全体学生信息表",key,list);
    }

    @Override
    public pageUtils FuzzySearchStudent(int pageNum, int pageSize, String[] name, String[] min, String[] max) {
        if(min.length != max.length || min.length != name.length) throw new RuntimeException("长度不匹配");
        try{
            HashMap<String, Range> RangeMap = filterCommon(name, min, max);
            List<Student> data = dao.FuzzySearch(pageNum, pageSize, null, RangeMap);
            List<String> list = tagDao.getTableTagName("1");
            List<String> header = new ArrayList<String>();
            header.add("学号");
            header.add("姓名");
            header.add("系号");
            return new pageUtils("2","1",pageNum,data,header,"全体学生信息表","",list);

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public pageUtils searchStudentCourse(int pageNum, int pageSize, String key, String value) {
        key = CommonMap.StuMapping(key);
        HashMap<String, String> map = CommonMap.getCommonMap("sno", "sname", "cno", "cname", "sdept");
        if(!key.equals("")){
            map.put(key,value);
        }
        else{
            CommonMap.setAllInMap(map, value);
        }
        List<StudentCourse> data = dao.searchStudentCourse(pageNum, pageSize, map,null);
        List<String> list = tagDao.getTableTagName("2");
        List<String> header = new ArrayList<String>();
        header.add("学号");
        header.add("姓名");
        header.add("课程号");
        header.add("课程名");
        header.add("系号");
        return new pageUtils("2","2",pageNum,data,header,"全体学生选课表",key,list);
    }

    @Override
    public pageUtils searchStudentCourse(int pageNum, int pageSize, String[] name, String[] min, String[] max) {
        if(min.length != max.length || min.length != name.length) throw new RuntimeException("长度不匹配");
        try{
            HashMap<String, Range> RangeMap = filterCommon(name, min, max);
            List<StudentCourse> data = dao.searchStudentCourse(pageNum, pageSize, null, RangeMap);
            List<String> list = tagDao.getTableTagName("2");
            List<String> header = new ArrayList<String>();
            header.add("学号");
            header.add("姓名");
            header.add("课程号");
            header.add("课程名");
            header.add("系号");
            return new pageUtils("2","2",pageNum,data,header,"全体学生选课表","",list);

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private HashMap<String,Range> filterCommon(String[] name, String[] min, String[] max) {
        Integer[] mins = new Integer[name.length];
        Integer[] maxs = new Integer[name.length];

        for (int i = 0; i < name.length; i++) {
            name[i] = CommonMap.StuMapping(name[i]);

            if(min[i].equals("?")) mins[i] = Integer.MIN_VALUE;
            else mins[i] = Integer.parseInt(min[i]);

            if(max[i].equals("?")) maxs[i] = Integer.MAX_VALUE;
            else maxs[i] = Integer.parseInt(max[i]);
        }
        Range[] ranges = Range.generateRange(mins, maxs);
        return CommonMap.creatRangeArray(name, ranges);
    }

    @Override
    public pageUtils searchStudentGrade(int pageNum, int pageSize, String key, String value) {
        key = CommonMap.StuMapping(key);
        HashMap<String, String> map = CommonMap.getCommonMap("sno", "sname", "cno", "cname", "test_grade", "final_grade","general_grade");
        if(!key.equals("")){
            map.put(key,value);
        }
        else{
            CommonMap.setAllInMap(map, value);
        }
        List<StudentGrade> data = dao.searchStudentGrade(pageNum, pageSize, map,null);
        List<String> list = tagDao.getTableTagName("3");
        List<String> header = new ArrayList<String>();
        header.add("学号");
        header.add("姓名");
        header.add("课程号");
        header.add("课程名");
        header.add("考试成绩");
        header.add("平时成绩");
        header.add("总成绩");

        return new pageUtils("2","3",pageNum,data,header,"全体学生成绩表",key,list);
    }

    @Override
    public pageUtils searchStudentGrade(int pageNum, int pageSize, String[] name, String[] min, String[] max) {
        if(min.length != max.length || min.length != name.length) throw new RuntimeException("长度不匹配");
        try{
            HashMap<String, Range> RangeMap = filterCommon(name, min, max);
            List<StudentGrade> data = dao.searchStudentGrade(pageNum, pageSize, null, RangeMap);
            List<String> list = tagDao.getTableTagName("3");
            List<String> header = new ArrayList<String>();
            header.add("学号");
            header.add("姓名");
            header.add("课程号");
            header.add("课程名");
            header.add("考试成绩");
            header.add("平时成绩");
            header.add("总成绩");
            return new pageUtils("2","3",pageNum,data,header,"全体学生成绩表","",list);

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    @Transactional
    public Student addStudent(String sname, String ssex, int grade, String sdept, Date date) {
        try{
            boolean b = checkInf(sname, ssex, grade, sdept, date);
            if(!b) throw new RuntimeException("输入不符合规定");
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
            int length = id.getStuLength();
            String sno = IdCreator.create(length,4);

            //插入
            Student stu = new Student(sno,sname,ssex,grade,sdept,date);
            int i = dao.insertStudent(stu);

            //返回结果
            if(i == 0){
                return null;
            }
            else{
                idDao.setId(new Id(id.getStuLength() + 1,id.getTeacherLength(),0));
            }
            return stu;
        } catch (Exception e) {
            throw new RuntimeException("添加失败，可能的原因有：数据长度过大或者存在必填参数未填写（必填参数有：姓名，系号）");
        }
    }

    @Override
    @Transactional
    public void selectCourse(int courseId) {
        try{
            //判断课程是否存在且合理（课程存在且开课时间大于当前时间）
            Course course = courseDao.getCourseById(courseId);
            if(course == null) throw new RuntimeException("该课程不存在");
            if(course.getBeginTime().compareTo(new Date()) < 0){
                throw new RuntimeException("该课程已经开课了，无法进行选课");
            }

            //判断学生是否学分足够
            HashMap<String, String> cd = new HashMap<String, String>();
            cd.put("sno",UserContext.getCurrentUser().getUserNumber());
            List<StudentCourse> studentCourses = dao.searchStudentCourse(1, 100, cd, null);

            //   计算总分
            int sum = 0;
            for (StudentCourse c:
                    studentCourses) {
                Course courseById = courseDao.getCourseById(Integer.parseInt(c.getCno()));
                sum = courseById.getCredit() + sum;
            }
            if(sum + course.getCredit() > 15) throw new RuntimeException("总学分需要低于或等于15分，无法选择该门课程");

            //插入课程
            int i = dao.insertStudentCourse( UserContext.getCurrentUser().getUserNumber(),courseId);
            if(i == 0) throw new RuntimeException("插入失败，请稍后重试");

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Course> FindMySelectCourse() {
        try{
            List<Course> courses = new ArrayList<Course>();

            HashMap<String, String> map = CommonMap.getCommonMap("sno", "sname", "cno", "cname", "sdept");
            map.put("sno",UserContext.getCurrentUser().getUserNumber());
            List<StudentCourse> studentCourses = dao.searchStudentCourse(1, 100, map, null);
            for (StudentCourse c:
                    studentCourses) {
                Course courseById = courseDao.getCourseById(Integer.parseInt(c.getCno()));
                courseById.setTno(teacherDao.getTeacherById(courseById.getTno()).getTname());
                courses.add(courseById);
            }
            return courses;


        } catch (Exception e) {
            throw new RuntimeException("服务器出现错误");
        }

    }

    @Override
    public void leaveCourse(int courseId) {
        try{
            //判断课程是否已经开始（课程存在且开课时间大于当前时间）
            Course course = courseDao.getCourseById(courseId);
            if(course == null) throw new RuntimeException("该课程不存在");
            if(course.getBeginTime().compareTo(new Date()) < 0){
                throw new RuntimeException("该课程已经开课了，无法退出选课");
            }
            //删除课程
            int i = dao.deleteStudentCourse(UserContext.getCurrentUser().getUserNumber(),courseId);
            if(i == 0) throw new RuntimeException("删除失败，请稍后重试");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Student getStudentById(String sno) {
        return dao.getStudentById(sno);
    }

    @Override
    public List<StudentGrade> getStudentGrade(int courseId) {
        try{
            Course course = courseDao.getCourseById(courseId);
            String username = UserContext.getCurrentUser().getUserNumber();
            String status = UserContext.getCurrentUser().getIdentitys();
            if(course == null || (!course.getTno().equals(username) && !status.equals("ADMIN"))){
                return null;
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("cno",String.valueOf(courseId));
            return dao.searchStudentGrade(1,Integer.MAX_VALUE,map,null);
        } catch (Exception e) {
            return null;
        }
    }



    @Override
    public StudentGrade getStudentGradeSpecial(String sno, int cno) {
        return dao.getStudentGrade(sno,cno);
    }

    @Override
    @Transactional
    public void updateStudentGrade(StudentGrade grade) {
        try {
            dao.updateStudentCourse(grade);
        } catch (Exception e) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    @Transactional
    public void updateStudentInf(Student student) {
        try
        {
            dao.updateStudent(student);
        } catch (Exception e) {
            throw new RuntimeException("错误");
        }

    }

    @Override
    @Transactional

    public void deleteStudentInf(String sno) {
        try {
            List<Course> courses = new ArrayList<Course>();

            HashMap<String, String> map = CommonMap.getCommonMap("sno", "sname", "cno", "cname", "sdept");
            map.put("sno",sno);
            List<StudentCourse> studentCourses = dao.searchStudentCourse(1, 100, map, null);
            for (StudentCourse sc:
                 studentCourses) {
                dao.deleteStudentCourse(sno,Integer.parseInt(sc.getCno()));
            }
            userDao.deleteUserById(sno);
            dao.deleteStudentById(sno);
        } catch (Exception e) {
            throw new RuntimeException("服务器出现异常");
        }
    }

    @Override
    @Transactional
    public void deleteStudentCourse(String sno, int cno) {
        try {
            dao.deleteStudentCourse(sno, cno);
        } catch (Exception e) {
            throw new RuntimeException("服务器出现异常");
        }
    }

    @Override
    public Map<String, Integer> statistic() {
        HashMap<String, Integer> map = new HashMap<>();
        int studentNum = dao.countStudent();
        int teacherNum = teacherDao.countTeacher();
        int deptNum = deptDao.countDept();
        int courseNum = courseDao.countCourse();
        int userNum = userDao.countUser();
        map.put("学生数量",studentNum);
        map.put("老师数量",teacherNum);
        map.put("系数量",deptNum);
        map.put("课程数量",courseNum);
        map.put("用户数量",userNum);
        return map;
    }


    public boolean checkInf(String sname, String ssex, int grade, String sdept, Date date){

        if(!ssex.equals("男") && !ssex.equals("女")){
            return false;
        }
        if(deptDao.getDeptById(sdept) == null){
            return false;
        }
        return true;
    }


}
