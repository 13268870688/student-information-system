package com.system.sims.beans;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String sno;
    private String sname;
    private String ssex;
    private int adminsionGrade;
    private String sdept;
    private Date birthDate;
}
