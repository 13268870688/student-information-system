package com.system.sims.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class pageUtils{
    private String father;
    private String child;
    private int pageNum;
    private Object data;
    private List<String> tableHeader;
    private String header;
    private String key;
    private List<String> tagList;

    public pageUtils(String father, String child, int pageNum, Object data, List<String> tableHeader, String header, String key) {
        this.father = father;
        this.child = child;
        this.pageNum = pageNum;
        this.data = data;
        this.tableHeader = tableHeader;
        this.header = header;
        this.key = key;
    }

    public pageUtils(String father, String child, int pageNum, Object data, List<String> tableHeader, String header) {
        this.father = father;
        this.child = child;
        this.pageNum = pageNum;
        this.data = data;
        this.tableHeader = tableHeader;
        this.header = header;
    }
}
