package com.jimi.ozone_server.model.vo;

import com.jfinal.plugin.activerecord.Record;
import com.jimi.ozone_server.model.PreTask;
import com.jimi.ozone_server.model.Staff;
import com.jimi.ozone_server.model.Task;
import com.jimi.ozone_server.model.sql.SQL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 甘特图任务安排数据处理
 */
public class GannttScheduleVO {

    private Integer scheduleId;

    private Date beginTime;

    private Date endTime;

    private Date finishTime;

    private String taskName;

    private Long  taskId;

    private List<Staff> staffList;

    private  List<PreTask> preTaskList;

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public List<PreTask> getPreTaskList() {
        return preTaskList;
    }

    public void setPreTaskList(List<PreTask> preTaskList) {
        this.preTaskList = preTaskList;
    }

    public static GannttScheduleVO ganttSchedule(List<Record> records,Long taskId, List<PreTask>  preList){
        List<Staff> staffLists = new ArrayList<>();
        GannttScheduleVO scheduleVO = new GannttScheduleVO();
        //同一任务中有多个员工执行时具有多条任务安排信息，获取多条任务安排信息中相同部分
        if (records.size()>0) {
            scheduleVO.setScheduleId(records.get(0).getInt("id"));
            scheduleVO.setBeginTime(records.get(0).getDate("begin_time"));
            scheduleVO.setFinishTime(records.get(0).getDate("finish_time"));
            scheduleVO.setEndTime(records.get(0).getDate("end_time"));
        }
        if(taskId != null) {
            scheduleVO.setTaskId(taskId);
            scheduleVO.setTaskName(Task.dao.findById(taskId).getName());
        }
        //同一个任务安排中多个员工信息整合成一个staffLists
        for (Record record : records) {
            Integer staffId = record.getInt("staff_id");
            Staff staff = Staff.dao.findFirst(SQL.SELECT_STAFF_BY_ID,staffId);
            staffLists.add(staff);
            scheduleVO.setStaffList(staffLists);
        }
        if (preList.size()>0) {
            scheduleVO.setPreTaskList(preList);
        }
        return  scheduleVO;
    }
}
