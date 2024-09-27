package com.system.sims.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private String tno;
    private String tname;
    private String ssex;
    private String dno;
    private String study;
    private String direction;
    private String birthDate;
    private String job;
}
