package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.TaskTypeService;
import com.jimi.ozone_server.util.ResultUtil;

/*
 * 任务类型管理控制层
 */
public class TaskTypeController  extends Controller {
    public  static TaskTypeService taskTypeService= Enhancer.enhance(TaskTypeService.class);

    @Log("根据项目id增加任务类型")
    public  void  add(Integer projectId,String name){
        if (projectId==null||name==null){
            throw new ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=taskTypeService.add(projectId,name);
        renderJson(resultUtil);
    }

    @Log("根据任务类型id更新")
    public  void  update(Integer id,String name,Integer projectId){
        if (id==null||projectId==null||name==null){
            throw  new  ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=taskTypeService.update(id,name,projectId);
        renderJson(resultUtil);
    }

    @Log("根据任务类型id进行逻辑删除")
    public  void delete(Integer id){
        if (id==null){
            throw  new  ParameterException("参数不能为空");
        }
        ResultUtil resultUtil =taskTypeService.delete(id);
        renderJson(resultUtil);
    }

    //根据项目id获取任务类型
    @Log("根据项目id获取任务类型")
    public  void getList(Integer projectId){
        renderJson(ResultUtil.succeed(taskTypeService.getList(projectId)));
    }
}
