package com.jimi.ozone_server.model.vo;

import com.jfinal.plugin.activerecord.Record;
import com.jimi.ozone_server.model.Staff;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectVO {
    private int id;

    private String  name;

    private Date beginTime;

    private Date endTime;

    private String manager;

    public boolean deleted;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setManager(int manager) {
        Staff staff=Staff.dao.findById(manager);
        String managerName=staff.getName();
        this.manager = managerName;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public static List<ProjectVO> fillList(List<Record> records){
        List<ProjectVO> projectVOs = new ArrayList<>();
        for (Record record : records) {
            ProjectVO projectVO=new ProjectVO();
            projectVO.setId(record.getInt("id"));
            projectVO.setName(record.getStr("name"));
            projectVO.setBeginTime(record.getDate("begin_time"));
            projectVO.setEndTime(record.getDate("end_time"));
            projectVO.setManager(record.getInt("manager"));
            projectVOs.add(projectVO);
        }
        return  projectVOs;
    }
}
