package com.jimi.ozone_server.model.vo;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jimi.ozone_server.model.Task;
import com.jimi.ozone_server.model.sql.SQL;

import java.util.ArrayList;
import java.util.List;

public class TypeVO {
    private  int id;

    private  String name;

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
            List<GannttScheduleVO> taskList=new ArrayList<>();
            List<Task> tList =Task.dao.find(SQL.SELECT_TASK_BY_TYPEID,typeId);//根据任务类型获取所有任务
            for (Task tl:tList) {
                List<Record> sList = Db.find(SQL.SELECT_SCHEDULE_BY_TASKID, tl.getId());//根据任务id 获取任务安排
                GannttScheduleVO task = GannttScheduleVO.ganttSchedule(sList,tl.getId());
                taskList.add(task);
            }
            typeVo.setTaskList(taskList);
            TypeVos.add(typeVo);
        }
        return TypeVos;
    }
}
