package com.system.sims.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int cno;
    private String cname;
    private int credit;
    private String tno;
    private Date testTime;
    private Date beginTime;
    private int classHour;
    private String classLocation;
}
