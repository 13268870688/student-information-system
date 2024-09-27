package com.system.sims.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ajaxResponse {
    private int status;
    private Object data;
    private String inf;
}
