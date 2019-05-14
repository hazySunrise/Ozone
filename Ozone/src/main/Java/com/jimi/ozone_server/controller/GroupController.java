package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.GroupService;
import com.jimi.ozone_server.util.ResultUtil;


/*
 * 小组管理控制层
 */
public class GroupController extends Controller {
    private  static GroupService  groupService= Enhancer.enhance(GroupService.class);

    //增加小组
    @Log("增加小组")
    public void add(String name){
        if (name==null){
            throw  new ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=groupService.add(name);
        renderJson(resultUtil);
    }

    @Log("根据id删除小组")
    public void delete(Integer id){
        if (id==null){
            throw  new  ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=groupService.delete(id);
        renderJson(resultUtil);
    }

    @Log("根据小组id对信息进行更新")
    public  void update(Integer id,String name){
        if (id==null||name==null){
            throw new  ParameterException("参数不能为空");
        }
        ResultUtil resultUtil=groupService.update(id,name);
        renderJson(resultUtil);
    }

    @Log("获取所有的小组")
    public  void getList(){
        renderJson(ResultUtil.succeed(groupService.getList()));
    }
}
