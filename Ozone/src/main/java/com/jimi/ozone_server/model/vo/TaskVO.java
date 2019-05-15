package com.jimi.ozone_server.model.vo;

import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

public class TaskVO {
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<TaskVO> fillList(List<Record> records) {
        List<TaskVO> taskVOs = new ArrayList<>();
        for (Record record : records) {
            TaskVO taskVO = new TaskVO();
            taskVO.setId(record.getInt("id"));
            taskVO.setName(record.getStr("name"));
            taskVOs.add(taskVO);
        }
        return taskVOs;
    }
}
