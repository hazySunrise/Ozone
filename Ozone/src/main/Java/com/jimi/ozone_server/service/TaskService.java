package com.jimi.ozone_server.service;

import com.jfinal.plugin.activerecord.Db;
import com.jimi.ozone_server.exception.OperationException;
import com.jimi.ozone_server.model.PreTask;
import com.jimi.ozone_server.model.Task;
import com.jimi.ozone_server.model.TaskSchedule;
import com.jimi.ozone_server.model.TaskType;
import com.jimi.ozone_server.model.sql.SQL;
import com.jimi.ozone_server.util.ResultUtil;

import java.util.Date;
import java.util.List;

/*
 * 任务管理逻辑层
 */
public class TaskService {


    //增加任务
    public  void addTask(String name,Integer typeId){
        TaskType taskType=TaskType.dao.findById(typeId);
        if (taskType==null){
            throw new OperationException("该任务类型不存在");
        }
        Task t=Task.dao.findFirst(SQL.SELECT_TASK_BY_TYPEID_AND_NAME,name,typeId); //判断任务是否已经存在
        if(t!=null){
            throw  new OperationException("该任务在此任务类型下已存在");
        }
        Task task=new Task();
        task.setName(name);
        task.setTypeId(Long.valueOf(typeId));
        task.setDeleted(false);
        task.save();
    }

    //更新任务
    public  void updateTask(Integer id,String name,Integer typeId){
        Task task=Task.dao.findById(id);
        if (task==null){
            throw new  OperationException("该任务不存在");
        }
        TaskType taskType=TaskType.dao.findById(typeId);
        if (taskType==null){
            throw new OperationException("该任务类型不存在");
        }
        Task t=Task.dao.findFirst(SQL.SELECT_TASK_ID_NOT,name,typeId,id); //判断任务是否已经存在
        if(t!=null){
            throw  new OperationException("该任务在此任务类型下已存在");
        }
        task.setName(name);
        task.setTypeId(Long.valueOf(typeId));
        task.update();
    }

    //增加任务安排
    public ResultUtil addSchedule(String name,Integer typeId,Date beignTime,Date endTime,Date finishTime,String staffId,Integer projectId,String preTaskId){
        Task task=Task.dao.findFirst(SQL.SELECT_TASK_BY_TYPEID_AND_NAME,name,typeId); //查找到新增加的任务
        Integer taskId=task.getInt("id");
        if (staffId!=null) {   //分割员工id，每个员工增加一条任务安排记录
            String[] staffs = staffId.split(",");
            TaskSchedule taskSchedule;
            for (String staff : staffs) {
                taskSchedule = new TaskSchedule();
                taskSchedule.setBeginTime(beignTime);
                taskSchedule.setEndTime(endTime);
                taskSchedule.setFinishTime(finishTime);
                taskSchedule.setProjectId(projectId.longValue());
                taskSchedule.setStaffId(Long.valueOf(staff));
                taskSchedule.setTaskId(Long.valueOf(taskId));
                taskSchedule.setDeleted(false);
                taskSchedule.save();
            }
        }else {
            TaskSchedule ts=new TaskSchedule();
            ts.setProjectId(projectId.longValue());
            ts.setTaskId(Long.valueOf(taskId));
            ts.setBeginTime(beignTime);
            ts.setEndTime(endTime);
            ts.setFinishTime(finishTime);
            ts.setDeleted(false);
            ts.save();
        }
        if (preTaskId!=null){
            //增加前置任务
            String[] preTasks=preTaskId.split(",");
            for (String pre:preTasks) {
                PreTask preTask = new PreTask();
                preTask.setDeleted(false);
                preTask.setPreTask(Long.valueOf(pre));
                preTask.setTaskId(Long.valueOf(taskId));
                preTask.save();
            }
        }
        return ResultUtil.succeed();
    }


    //更新任务安排,先删后增
    public  ResultUtil updateSchedule(Integer taskId,Date beignTime,Date endTime,Date finishTime,String staffId,Integer projectId,String preTaskId){
        Db.delete(SQL.SELECT_SCHEDULE_BY_TASID,taskId);//根据任务id删除所有的任务安排记录
        if (staffId!=null) {
            String[] staffs = staffId.split(",");
            TaskSchedule taskSchedule;
            for (String staff : staffs) {
                taskSchedule = new TaskSchedule();
                taskSchedule.setBeginTime(beignTime);
                taskSchedule.setEndTime(endTime);
                taskSchedule.setFinishTime(finishTime);
                taskSchedule.setTaskId(Long.valueOf(taskId));
                taskSchedule.setProjectId(projectId.longValue());
                taskSchedule.setStaffId(Long.valueOf(staff));
                taskSchedule.setDeleted(false);
                taskSchedule.save();
            }
        }else {
            TaskSchedule ts=new TaskSchedule();
            ts.setTaskId(Long.valueOf(taskId));
            ts.setBeginTime(beignTime);
            ts.setEndTime(endTime);
            ts.setFinishTime(finishTime);
            ts.setProjectId(projectId.longValue());
            ts.setDeleted(false);
            ts.save();
        }
        Db.delete(SQL.DELETE_PERTASK,taskId);//根据任务id删除前置任务
        if (preTaskId!=null){
            //增加前置任务
            String[] preTasks=preTaskId.split(",");
            for (String pre:preTasks) {
                PreTask preTask = new PreTask();
                preTask.setDeleted(false);
                preTask.setPreTask(Long.valueOf(pre));
                preTask.setTaskId(Long.valueOf(taskId));
                preTask.save();
            }
        }
        return ResultUtil.succeed();
    }


    //判断员工是否任务时间冲突
    public  boolean timeConflict(String  staffIds,Date beginTime,Date endTime){
        boolean flag=false;
        String[] staffs=staffIds.split(",");//分割多个员工
        for (String sId:staffs) {
            Integer staffId=Integer.parseInt(sId);
            TaskSchedule t = TaskSchedule.dao.findFirst(SQL.COUNT_CONFLICT_SQL, beginTime, endTime, beginTime, endTime, beginTime, endTime, beginTime, endTime, staffId);
            if (t.get("quantity") != null) {
                Integer quantity = Integer.parseInt(t.get("quantity").toString());
                if (quantity > 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    //删除任务
    public ResultUtil delete(Integer id){
        Task task =Task.dao.findById(id);
        if (task==null){
            throw  new OperationException("任务不存在");
        }
        task.setDeleted(true);
        task.update();
        return  ResultUtil.succeed();
    }

    //任务安排冲突时，将原先添加成功的任务记录删除
    public void deleteNewTask(String name,Integer typeId){
        Task task=Task.dao.findFirst(SQL.SELECT_TASK_BY_TYPEID_AND_NAME,name,typeId); //查找到新增加的任务
        task.delete();
    }

    //获取某项目下所有的任务
    public List<Task> getAllTask(Integer projectId){
       List<Task> taskList=Task.dao.find(SQL.SELECT_ALL_TASK_BY_PROJECTID,projectId);
       return  taskList;
    }

}
