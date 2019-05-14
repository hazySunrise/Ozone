package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.ProjectService;
import com.jimi.ozone_server.util.ResultUtil;

import java.util.Date;


/*
 * 项目管理控制层
 */
public class ProjectController extends Controller {
    private static ProjectService projectService= Enhancer.enhance(ProjectService.class);

    //添加项目
    @Log("添加项目")
    public  void  add(String name, Date beginTime, Date endTime, Integer manager){
        if (name==null){
            throw new ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=projectService.add(name,beginTime,endTime,manager);
        renderJson(resultUtil);
    }

    //删除项目
    @Log("根据id删除项目，将deleted设为1")
    public  void delete(Integer id){
        if (id==null){
            throw new ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=projectService.deleted(id);
        renderJson(resultUtil);
    }

    //更新项目
    @Log("根据项目id更新项目")
    public  void update(Integer id,String name, Date beginTime,Date endTime,Integer manager){
        if (id==null||name==null){
            throw new ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=projectService.update(id,name,beginTime,endTime,manager);
        renderJson(resultUtil);
    }

    //获取所有项目列表
    @Log("获取所有项目列表")
    public  void  getList(){
        renderJson(ResultUtil.succeed(projectService.getList()));
    }

}
