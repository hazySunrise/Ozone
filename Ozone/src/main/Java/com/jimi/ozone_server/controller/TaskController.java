package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.OperationException;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.TaskService;
import com.jimi.ozone_server.util.ResultUtil;

import java.util.Date;

/*
 * 任务管理控制层
 */
public class TaskController extends Controller {
    public  static TaskService taskService= Enhancer.enhance(TaskService.class);

    @Log("preTaskId为前置任务，多个时以\",\"隔开，staffId为员工id，多个时以\",\"隔开，多个时，force代表是否强制执行")
    public  void add(String name, Integer typeId, Integer projectId,Boolean force,String preTaskId, Date beginTime,Date endTime,Date finishTime,String staffId){
        if(name==null||projectId==null||typeId==null||force==null){
            throw new ParameterException("参数不能为空");
        }
        taskService.addTask(name,typeId);//增加任务
        ResultUtil tsResult;
        if (finishTime==null){
            if(staffId==null||beginTime==null||endTime==null){
                tsResult=taskService.addSchedule(name,typeId,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//增加任务安排
            }else {
                if (force){  //强制执行
                    tsResult =taskService.addSchedule(name,typeId,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//增加任务安排
                }else {
                    //判断员工是否已有任务
                    if (taskService.timeConflict(staffId,beginTime,endTime)){ //时间冲突
                        taskService.deleteNewTask(name,typeId);//删除刚增加的任务
                        throw new OperationException("员工在该时间段已有任务");
                    }else {
                        tsResult =taskService.addSchedule(name,typeId,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//增加任务安排
                    }
                }
            }
        }else {
            tsResult =taskService.addSchedule(name,typeId,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//增加任务安排
        }
        renderJson(tsResult);
    }

    @Log("根据id逻辑删除任务")
    public void delete(Integer id){
        if (id==null){
            throw  new ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=taskService.delete(id);
        renderJson(resultUtil);
    }


    @Log("更新任务，前置任务，任务安排记录")
    public void update(Integer id,String name, Integer typeId, Integer projectId,Boolean force,String preTaskId, Date beginTime,Date endTime,Date finishTime,String staffId){
        if (id==null||name==null||force==null||typeId==null||projectId==null){
            throw new  OperationException("参数不能为空");
        }
        taskService.updateTask(id,name,typeId);
        ResultUtil tsResult;
        if (finishTime==null){
            if(staffId==null||beginTime==null||endTime==null){
                tsResult=taskService.updateSchedule(id,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//更新任务安排
            }else {
                if (force){  //强制执行
                    tsResult=taskService.updateSchedule(id,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//更新任务安排
                }else {
                    //判断员工是否已有任务
                    if (taskService.timeConflict(staffId,beginTime,endTime)){ //时间冲突
                        throw new OperationException("员工在该时间段已有任务");
                    }else {
                        tsResult=taskService.updateSchedule(id,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//更新任务安排
                    }
                }
            }
        }else {
            tsResult=taskService.updateSchedule(id,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//更新任务安排
        }
        renderJson(tsResult);
    }

    @Log("获取某个项目下所有的任务")
    public void getAllTask(Integer projectId){
        if (projectId==null){
            throw new  ParameterException("参数不能为空");
        }
        renderJson(ResultUtil.succeed(taskService.getAllTask(projectId)));
    }
}
