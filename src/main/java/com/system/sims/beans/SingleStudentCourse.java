package com.system.sims.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleStudentCourse {
    private String sno;
    private int cno;
    private int testGrade;
    private int generalGrade;
    private int finalGrade;
}
