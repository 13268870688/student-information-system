package com.system.sims.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentGrade {
    private String sno;
    private String sname;
    private String cno;
    private String cname;
    private int testGrade;
    private int generalGrade;
    private int finalGrade;

}
