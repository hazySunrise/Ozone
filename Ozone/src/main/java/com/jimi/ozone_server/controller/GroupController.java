package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.GroupService;
import com.jimi.ozone_server.util.ResultFactory;


/*
 * 小组管理控制层
 */
public class GroupController extends Controller {
    private  static GroupService  groupService = Enhancer.enhance(GroupService.class);

    //增加小组
    @Log("增加name为{name}的小组")
    public void add(String name){
        if (name == null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory = groupService.add(name);
        renderJson(resultFactory);
    }

    @Log("对id为{id}的小组进行逻辑删除")
    public void delete(Integer id){
        if (id == null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory = groupService.delete(id);
        renderJson(resultFactory);
    }

    @Log("更新id为{id}的小组信息，更新后的小组名为{name}")
    public  void update(Integer id,String name){
        if (id == null||name == null){
            throw new ParameterException("参数不能为空");
        }
        ResultFactory resultFactory = groupService.update(id,name);
        renderJson(resultFactory);
    }

    @Log("获取所有的小组列表，内容包括小组id和小组姓名")
    public  void getList(){
        renderJson(ResultFactory.succeed(groupService.getList()));
    }
}
