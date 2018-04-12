package com.kilo.domain;

/**
 * 实战课程访问数量实体类
 * Created by kilo on 2018/4/12.
 */
public class CourseClickCount {
    private String name;
    private long value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
