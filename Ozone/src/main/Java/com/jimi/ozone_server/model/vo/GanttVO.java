package com.jimi.ozone_server.model.vo;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jimi.ozone_server.model.Project;
import com.jimi.ozone_server.model.Staff;
import com.jimi.ozone_server.model.sql.SQL;

import java.util.Date;
import java.util.List;

public class GanttVO {
    private  Long id;

    private  String name;

    private  String manager;

    private Date beginTime;

    private  Date endTime;

    private int period;

   private  List<TypeVO> typeList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public List<TypeVO> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<TypeVO> typeList) {
        this.typeList = typeList;
    }

    public static GanttVO gantt(Integer projectId){
        Project project=Project.dao.findById(projectId);

        GanttVO ganttVO=new GanttVO();
        ganttVO.setId(project.getId());
        ganttVO.setName(project.getName());
        Date  beginTime=project.getBeginTime();
        Date endTime=project.getEndTime();
        ganttVO.setBeginTime(beginTime);
        ganttVO.setEndTime(endTime);
        int day = (int)((endTime.getTime()-beginTime.getTime())/(3600*1000*24));//相减的毫秒换算成天数
        ganttVO.setPeriod(day);
        Long manager=project.getManager();
        ganttVO.setManager(Staff.dao.findById(manager).getName());
        List<Record> typeList= Db.find(SQL.SELECT_TYPE_BY_GROUPID,projectId);//获取项目下所有的任务类型
        List<TypeVO> typeVoList= TypeVO.fillList(typeList);
        ganttVO.setTypeList(typeVoList);
        return  ganttVO;
   }
}
