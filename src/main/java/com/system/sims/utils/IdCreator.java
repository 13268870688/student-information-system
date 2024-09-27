package com.system.sims.utils;
import java.util.Calendar;

public class IdCreator {
    public static String create(int length,int max){
        if(String.valueOf(length).length() > max || String.valueOf(length + 1).length() > max) throw new RuntimeException("超过规定长度");
        Calendar instance = Calendar.getInstance();

        String y = String.valueOf(instance.get(Calendar.YEAR));
        String m = String.format("%02d",instance.get(Calendar.MONTH) + 1);
        String d = String.format("%02d",instance.get(Calendar.DATE));
        String format = String.format("%0" + max + "d", length + 1);

        return y + m + d + format;
    }


}
