package com.jimi.ozone_server.model.vo;

import com.jfinal.plugin.activerecord.Record;
import com.jimi.ozone_server.model.Project;
import com.jimi.ozone_server.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleVO {
    private  Integer id;

    private Date beginTime;

    private  Date finishTime;

    private  String projectName;

    private  String taskName;

    private Integer  taskId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public static List<ScheduleVO> fillList(List<Record> records){
        List<ScheduleVO> scheduleVOs=new ArrayList<>();
        for (Record record : records) {
            ScheduleVO staffVO=new ScheduleVO();
            staffVO.setId(record.getInt("id"));
            staffVO.setBeginTime(record.getDate("begin_time"));
            Date finisTime=record.getDate("finish_time");
            if (finisTime!=null){
                staffVO.setFinishTime(finisTime);
            }else {
                staffVO.setFinishTime(record.getDate("end_time"));
            }
            int projectId=record.getInt("project_id");
            int taskId=record.getInt("task_id");
            staffVO.setTaskId(taskId);
            staffVO.setProjectName(Project.dao.findById(projectId).getName());
            staffVO.setTaskName(Task.dao.findById(taskId).getName());
            scheduleVOs.add(staffVO);
        }
        return  scheduleVOs;
    }

}
