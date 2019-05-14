package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.ProjectService;
import com.jimi.ozone_server.util.ResultFactory;

import java.util.Date;


/*
 * 项目管理控制层
 */
public class ProjectController extends Controller {
    private static ProjectService projectService=Enhancer.enhance(ProjectService.class);

    //添加项目
    @Log("添加名称为{name}的项目，项目的开始时间为{beginTime},结束时间为{endTime},项目负责人id为{manager}")
    public  void  add(String name, Date beginTime, Date endTime, Integer manager){
        if (name==null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory =projectService.add(name,beginTime,endTime,manager);
        renderJson(resultFactory);
    }

    //删除项目
    @Log("根据{id}删除项目，将刚项目的deleted设为1")
    public  void delete(Integer id){
        if (id==null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory =projectService.deleted(id);
        renderJson(resultFactory);
    }

    //更新项目
    @Log("更新id为{id}的项目，更新后的项目名为{name}，开始时间为{beginTime},结束时间为{endTime},项目负责人id为{manager}")
    public  void update(Integer id,String name, Date beginTime,Date endTime,Integer manager){
        if (id==null||name==null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory =projectService.update(id,name,beginTime,endTime,manager);
        renderJson(resultFactory);
    }

    //获取所有项目列表
    @Log("获取所有项目列表，内容包括项目id和项目名称")
    public  void  getList(){
        renderJson(ResultFactory.succeed(projectService.getList()));
    }

}
