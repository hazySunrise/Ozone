package com.jimi.ozone_server.controller;

import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jimi.ozone_server.annotation.Log;
import com.jimi.ozone_server.exception.ParameterException;
import com.jimi.ozone_server.service.DisplayService;
import com.jimi.ozone_server.util.ResultUtil;

/*
 * 主页面显示管理控制层
 */
public class DisplayController extends Controller {

    private  static DisplayService displayService=  Enhancer.enhance(DisplayService.class);

    @Log("人力资源界面显示")
    public  void  resource(){
        renderJson(ResultUtil.succeed(displayService.resource()));
    }

    @Log("甘特图显示")
    public  void ganttChart(Integer projectId){
        if (projectId==null){
            throw  new ParameterException("参数不能为空");
        }
        renderJson(ResultUtil.succeed(displayService.ganttChart(projectId)));
    }

}
