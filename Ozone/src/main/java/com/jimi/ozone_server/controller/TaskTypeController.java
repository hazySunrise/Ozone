package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.TaskTypeService;
import com.jimi.ozone_server.util.ResultFactory;

/*
 * 任务类型管理控制层
 */
public class TaskTypeController  extends Controller {
    public  static TaskTypeService taskTypeService=Enhancer.enhance(TaskTypeService.class);

    @Log("增加name为{name}的任务类型，所属项目的id为{projectId}")
    public  void  add(Integer projectId,String name){
        if (projectId==null||name==null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory=taskTypeService.add(projectId,name);
        renderJson(resultFactory);
    }

    @Log("更新id为{id}的任务类型，更新后的名称为{name}，所属项目为{projectId}")
    public  void  update(Integer id,String name,Integer projectId){
        if (id==null||projectId==null||name==null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory=taskTypeService.update(id,name,projectId);
        renderJson(resultFactory);
    }

    @Log("对id为{id}的任务类型进行逻辑删除")
    public  void delete(Integer id){
        if (id==null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory=taskTypeService.delete(id);
        renderJson(resultFactory);
    }

    //根据项目id获取任务类型
    @Log("根据项目id{projectId}获取该项目下的任务类型")
    public  void getList(Integer projectId){
        renderJson(ResultFactory.succeed(taskTypeService.getList(projectId)));
    }
}
