package com.jimi.ozone_server.model.vo;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jimi.ozone_server.model.sql.SQL;

import java.util.ArrayList;
import java.util.List;

public class StaffVO {
    private  int id;

    private String name;

    private  List<ScheduleVO> taskList;

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

    public List<ScheduleVO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ScheduleVO> taskList) {
        this.taskList = taskList;
    }

    public static List<StaffVO> fillList(List<Record> records){
        List<StaffVO> staffVOS=new ArrayList<>();
        for (Record record : records) {
            StaffVO staffVO=new StaffVO();
            int staffId=record.getInt("id");
            staffVO.setId(staffId);
            staffVO.setName(record.getStr("name"));
            List<Record> ts= Db.find(SQL.SELECT_SCHEDULE_BY_STAFFID,staffId);
            if (ts.size()!=0) {
                List<ScheduleVO> taskList = ScheduleVO.fillList(ts);
                staffVO.setTaskList(taskList);
            }
            staffVOS.add(staffVO);
        }
        return  staffVOS;
    }
}
