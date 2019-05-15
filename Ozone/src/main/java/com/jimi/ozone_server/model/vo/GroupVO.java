package com.jimi.ozone_server.model.vo;

import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

public class GroupVO {
    private int id;

    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<GroupVO> fillList(List<Record> records){
        List<GroupVO> groupVOs = new ArrayList<>();
        for (Record record : records) {
            GroupVO groupVO = new GroupVO();
            groupVO.setId(record.getInt("id"));
            groupVO.setName(record.getStr("name"));
            groupVOs.add(groupVO);
        }
        return groupVOs;
    }
}
