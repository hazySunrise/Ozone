package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.OperationException;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.TaskService;
import com.jimi.ozone_server.util.ResultFactory;

import java.util.Date;

/*
 * 任务管理控制层
 */
public class TaskController extends Controller {
    public static TaskService taskService = Enhancer.enhance(TaskService.class);

    @Log("添加任务名为{name}，任务类型为{typeId}的任务；添加前置任务为{preTaskId}的前置任务；添加员工id为{staffId}，任务开始时间为{beginTime}，" +
            "计划结束时间为{endTime}，实际任务完成时间为{finishTime}的时间安排记录；force值为{force}(0代表不强制执行，1代表强制执行添加操作")
    public void add(String name, Integer typeId, Integer projectId,Boolean force,String preTaskId, Date beginTime,Date endTime,Date finishTime,String staffId){
        if(name == null||projectId == null||typeId == null||force == null){
            throw new ParameterException("参数不能为空");
        }
        taskService.addTask(name,typeId);//增加任务
        ResultFactory tsResult;
        if (finishTime == null){
            if(staffId == null||beginTime == null||endTime == null){
                tsResult = taskService.addSchedule(name,typeId,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//增加任务安排
            }else {
                if (force){  //强制执行
                    tsResult = taskService.addSchedule(name,typeId,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//增加任务安排
                }else {
                    //判断员工是否已有任务
                    if (taskService.timeConflict(staffId,beginTime,endTime)){ //时间冲突
                        taskService.deleteNewTask(name,typeId);//删除刚增加的任务
                        throw new OperationException("员工在该时间段已有任务");
                    }else {
                        tsResult = taskService.addSchedule(name,typeId,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//增加任务安排
                    }
                }
            }
        }else {
            tsResult = taskService.addSchedule(name,typeId,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//增加任务安排
        }
        renderJson(tsResult);
    }

    @Log("对id为{id}的任务进行逻辑删除")
    public void delete(Integer id){
        if (id == null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory =taskService.delete(id);
        renderJson(resultFactory);
    }


    @Log("更新id为{id}的任务，更新后任务名name为{name}，任务类型id为{typeId}；更新任务前置任务为{preTaskId}；更新任务安排记录，更新后员工的id为{staffId}，" +
            "任务开始时间为{beginTime}， 计划结束时间为{endTime}，实际任务完成时间为{finishTime}的时间安排记录；；force值为{force}(0代表不强制执行，1代表强制执行添加操作")
    public void update(Integer id,String name, Integer typeId, Integer projectId,Boolean force,String preTaskId, Date beginTime,Date endTime,Date finishTime,String staffId){
        if (id == null||name == null||force == null||typeId == null||projectId == null){
            throw new OperationException("参数不能为空");
        }
        taskService.updateTask(id,name,typeId);
        ResultFactory tsResult;
        if (finishTime == null){
            if(staffId == null||beginTime == null||endTime == null){
                tsResult = taskService.updateSchedule(id,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//更新任务安排
            }else {
                if (force){  //强制执行
                    tsResult = taskService.updateSchedule(id,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//更新任务安排
                }else {
                    //判断员工是否已有任务
                    if (taskService.timeConflict(staffId,beginTime,endTime)){ //时间冲突
                        throw new OperationException("员工在该时间段已有任务");
                    }else {
                        tsResult = taskService.updateSchedule(id,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//更新任务安排
                    }
                }
            }
        }else {
            tsResult = taskService.updateSchedule(id,beginTime,endTime,finishTime,staffId,projectId,preTaskId);//更新任务安排
        }
        renderJson(tsResult);
    }

    @Log("获取项目id为{projectId}下所有的任务，内容包括任务id和任务名称")
    public void getAllTask(Integer projectId){
        if (projectId == null){
            throw new ParameterException("参数不能为空");
        }
        renderJson(ResultFactory.succeed(taskService.getAllTask(projectId)));
    }
}
