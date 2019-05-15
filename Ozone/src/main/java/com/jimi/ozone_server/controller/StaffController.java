package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.StaffService;
import com.jimi.ozone_server.util.ResultFactory;


/*
 * 员工管理控制层
 */
public class StaffController extends Controller {
    private static StaffService staffService = Enhancer.enhance(StaffService.class);

    @Log("增加姓名为{name}的员工，所在小组id为{groupId}")
    public void add(String name,Integer groupId){
        if (name == null||groupId == null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory=staffService.add(name,groupId);
        renderJson(resultFactory);
    }

    @Log("对id为{id}的员工信息进行逻辑删除")
    public void delete(Integer id){
        if (id == null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory=staffService.delete(id);
        renderJson(resultFactory);
    }

    @Log("更新id为{id}的员工信息，更新后的员工姓名为{name}，所在小组id为{groupId}")
    public void update(Integer id,String name,Integer groupId){
        if (id == null||name == null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory = staffService.update(id,name,groupId);
        renderJson(resultFactory);
    }

    @Log("获取小组id为{groupId}下的所有员工，内容包括员工id和员工姓名")
    public void getStaff(Integer groupId){
        if (groupId == null){
            throw new ParameterException("参数不能为空");
        }
        renderJson(ResultFactory.succeed(staffService.getStaff(groupId)));
    }

}
