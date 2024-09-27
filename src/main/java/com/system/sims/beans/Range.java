package com.system.sims.beans;




public class Range {

    public static final int GREATER_THAN = 1;
    public static final int LESS_THAN = 2;
    public static final int LESS_THAN_OR_EQUAL = 3;
    public static final int GREATER_THAN_OR_EQUAL = 4;
    public static final int EQUAL = 5;

    public int min;
    public int max;

    public Range(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    public static Range[] generateRange(Integer[] mins,Integer[] maxs){
        if(mins.length != maxs.length) return null;
        Range[] p = new Range[mins.length];
        for (int i = 0; i < mins.length; i++) {
            Range range = new Range(mins[i],maxs[i]);
            p[i] = range;
        }

        return p;
    }
}
