package com.jimi.ozone_server.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jimi.ozone_server.model.PreTask;
import com.jimi.ozone_server.model.Task;
import com.jimi.ozone_server.model.sql.SQL;
import com.jimi.ozone_server.model.vo.*;

import java.util.ArrayList;
import java.util.List;

/*
 * 主页面管理逻辑层
 */
public class DisplayService {

    public List<DisplayVO> resource(){
        List<Record> list =  Db.find(SQL.SELECT_ALL_GROUP);
        for (Record groupRecord : list) {
            int groupId = groupRecord.getInt("id");
            List<Record> staffList = Db.find(SQL.SELECT_STAFF_BY_GROUPID,groupId);
            for (Record staffRecord : staffList) {
                int staffId = staffRecord.getInt("id");
                List<Record> ts = Db.find(SQL.SELECT_SCHEDULE_BY_STAFFID,staffId);
                if (ts.size() ! = 0) {
                    List<ScheduleVO> taskList = ScheduleVO.fillList(ts);
                    staffRecord.set("taskList",taskList);
                }
            }
            List<StaffVO> staffVOS = StaffVO.fillList(staffList);
            groupRecord.set("staffList",staffVOS);
        }
        List<DisplayVO> disList = DisplayVO.fillList(list);
        return disList;
    }

    public GanttVO ganttChart(Integer projectId){
        List<Record> typeList = Db.find(SQL.SELECT_TYPE_BY_GROUPID,projectId);//获取项目下所有的任务类型
        for (Record record : typeList) {
            int typeId = record.getInt("id");
            List<GannttScheduleVO> taskList = new ArrayList<>();
            List<Task> tList = Task.dao.find(SQL.SELECT_TASK_BY_TYPEID,typeId);//根据任务类型获取所有任务
            for (Task tl:tList) {
                Long taskId = tl.getId();
                //同一个任务安排中多个前置任务整合成一个preList
                List<PreTask>  preList = PreTask.dao.find(SQL.SELECT_PRETASK_BY_TASKID,taskId);
                List<Record> sList = Db.find(SQL.SELECT_SCHEDULE_BY_TASKID, taskId);//根据任务id 获取任务安排
                GannttScheduleVO task = GannttScheduleVO.ganttSchedule(sList,taskId,preList);
                taskList.add(task);
            }
            record.set("taskList",taskList);
        }
        List<TypeVO> typeVoList = TypeVO.fillList(typeList);
        GanttVO ganttVO = GanttVO.gantt(projectId,typeVoList);
        return  ganttVO;
    }

}
