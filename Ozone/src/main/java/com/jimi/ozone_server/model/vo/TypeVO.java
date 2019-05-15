package com.jimi.ozone_server.model.vo;

import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

public class TypeVO {
    private int id;

    private String name;

    private List<GannttScheduleVO> taskList;

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

    public List<GannttScheduleVO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<GannttScheduleVO> taskList) {
        this.taskList = taskList;
    }

    public static List<TypeVO> fillList(List<Record> records) {
        List<TypeVO> TypeVos = new ArrayList<>();
        for (Record record : records) {
            TypeVO typeVo = new TypeVO();
            int typeId=record.getInt("id");
            typeVo.setId(typeId);
            typeVo.setName(record.getStr("name"));
            typeVo.setTaskList(record.get("taskList"));
            TypeVos.add(typeVo);
        }
        return TypeVos;
    }
}
