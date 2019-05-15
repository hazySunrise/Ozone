package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.DisplayService;
import com.jimi.ozone_server.util.ResultFactory;

/*
 * 主页面显示管理控制层
 */
public class DisplayController extends Controller {

    private static DisplayService displayService = Enhancer.enhance(DisplayService.class);

    @Log("显示人力资源界面")
    public void  resource(){
        renderJson(ResultFactory.succeed(displayService.resource()));
    }

    @Log("获取项目id为{projectId}的甘特图显示")
    public void ganttChart(Integer projectId){
        if (projectId == null){
            throw new ParameterException("参数不能为空");
        }
        renderJson(ResultFactory.succeed(displayService.ganttChart(projectId)));
    }

}
