package com.jimi.ozone_server.model.vo;

import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

public class DisplayVO {

    private  Integer id;

    private String groupName;

    private List<StaffVO> staffList;

    public List<StaffVO> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<StaffVO> staffList) {
        this.staffList = staffList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public static List<DisplayVO> fillList(List<Record> records) {
        List<DisplayVO> displayVOs = new ArrayList<DisplayVO>();
        for (Record record : records) {
            DisplayVO displayVO = new DisplayVO();
            int groupId=record.getInt("id");
            displayVO.setId(groupId);
            displayVO.setGroupName(record.getStr("name"));
            displayVO.setStaffList(record.get("staffList"));
            displayVOs.add(displayVO);
        }
        return displayVOs;
    }

}
