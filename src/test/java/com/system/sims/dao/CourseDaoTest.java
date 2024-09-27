package com.system.sims.dao;

import com.system.sims.SimsApplication;
import com.system.sims.beans.Course;
import com.system.sims.beans.Range;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SimsApplication.class)
class CourseDaoTest {
    @Resource
    private CourseDao dao;

    @Test
    void getCourseByCondition() {
        Calendar calendar= Calendar.getInstance();
        calendar.set(2023, 10, 10);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(calendar.getTime());

        List<Course> testTime = dao.getCourseByCondition("begin_time",format, Range.GREATER_THAN);

        System.out.println(testTime);
    }
}