package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.StaffService;
import com.jimi.ozone_server.util.ResultUtil;


/*
 * 员工管理控制层
 */
public class StaffController extends Controller {
    private  static StaffService staffService= Enhancer.enhance(StaffService.class);

    @Log("增加员工")
    public void add(String name,Integer groupId){
        if (name==null||groupId==null){
            throw  new ParameterException("参数不能为空");
        }
        ResultUtil resultUtil =staffService.add(name,groupId);
        renderJson(resultUtil);
    }

    @Log("根据id删除员工信息")
    public void delete(Integer id){
        if (id==null){
            throw  new ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=staffService.delete(id);
        renderJson(resultUtil);
    }

    @Log("根据员工id更新员工信息")
    public void update(Integer id,String name,Integer groupId){
        if (id==null||name==null){
            throw  new  ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=staffService.update(id,name,groupId);
        renderJson(resultUtil);
    }

    @Log("获取某个小组下的所有员工")
    public void getStaff(Integer groupId){
        if (groupId==null){
            throw  new  ParameterException("参数不能为空");
        }
        renderJson(ResultUtil.succeed(staffService.getStaff(groupId)));
    }

}
