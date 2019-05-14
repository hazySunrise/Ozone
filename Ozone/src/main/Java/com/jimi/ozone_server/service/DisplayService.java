package com.jimi.ozone_server.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jimi.ozone_server.model.sql.SQL;
import com.jimi.ozone_server.model.vo.DisplayVO;
import com.jimi.ozone_server.model.vo.GanttVO;

import java.util.List;

/*
 * 主页面管理逻辑层
 */
public class DisplayService {

    public List<DisplayVO> resource(){
        List<Record> list=  Db.find(SQL.SELECT_ALL_GROUP);
        List<DisplayVO> disList=DisplayVO.fillList(list);
        return disList;
    }


    public  GanttVO ganttChart(Integer projectId){
        GanttVO ganttVO=GanttVO.gantt(projectId);
        return  ganttVO;

    }



}
