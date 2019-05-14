package com.jimi.ozone_server.service;

import com.jimi.ozone_server.exception.OperationException;
import com.jimi.ozone_server.model.Project;
import com.jimi.ozone_server.model.Staff;
import com.jimi.ozone_server.model.sql.SQL;
import com.jimi.ozone_server.util.ResultFactory;
import java.util.Date;
import java.util.List;

/*
 * 项目管理逻辑层
 */
public class ProjectService {

    public ResultFactory deleted(Integer id){
        Project project =Project.dao.findById(id);
        if (project==null){
            throw new OperationException("项目不存在");
        }
        project.setDeleted(true);
        project.update();
        return ResultFactory.succeed();
    }

    public ResultFactory add(String name, Date beginTime, Date endTime, Integer manager){
        if (Project.dao.find(SQL.SELECT_PROJECT_BY_NAME,name).size()!=0){
            throw new OperationException("项目已存在");
        }
        if (Staff.dao.findById(manager)==null){
            throw new OperationException("该人不存在");
        }
        Project p=new Project();
        p.setName(name);
        p.setBeginTime(beginTime);
        p.setEndTime(endTime);
        p.setManager(Long.valueOf(manager));
        p.setDeleted(false);
        p.save();
        return ResultFactory.succeed();
    }

    public ResultFactory update(Integer id, String name, Date beginTime, Date endTime, Integer manager){
        Project project =Project.dao.findById(id);
        if (project!=null){
            if (Project.dao.find(SQL.SELECT_PROJECT_BY_NAME,name).size()!=0){
                throw new OperationException("项目已存在");
            }
            if (Staff.dao.findById(manager)==null){
                throw new OperationException("该人不存在");
            }
            project.setName(name);
            project.setBeginTime(beginTime);
            project.setEndTime(endTime);
            project.setManager(Long.valueOf(manager));
            project.update();
        }else {
           throw new OperationException("项目不存在");
        }
        return ResultFactory.succeed();
    }

    public List<Project> getList(){
       List<Project> projects=Project.dao.find(SQL.SELECT_ALL_PROJECT);
        return projects;
    }
}
