package com.jimi.ozone_server.service;

import com.jimi.ozone_server.exception.OperationException;
import com.jimi.ozone_server.model.Project;
import com.jimi.ozone_server.model.TaskType;
import com.jimi.ozone_server.model.sql.SQL;
import com.jimi.ozone_server.util.ResultUtil;

import java.util.List;


/*
 * 任务类型管理逻辑层
 */
public class TaskTypeService {

    public ResultUtil add(Integer projectId,String name){
        if (Project.dao.findById(projectId)==null){
            throw  new OperationException("项目不存在");
        }
        if (TaskType.dao.find(SQL.SELECT_TASKTYPE_BY_NAME_AND_PROJECTID,name,projectId).size()!=0){
            throw  new  OperationException("该项目下任务类型已存在");
        }
        TaskType taskType=new TaskType();
        taskType.setProjectId(projectId.longValue());
        taskType.setName(name);
        taskType.setDeleted(false);
        taskType.save();
        return  ResultUtil.succeed();
    }

    public  ResultUtil delete(Integer id){
        TaskType taskType=TaskType.dao.findById(id);
        if (taskType==null){
            throw new OperationException("任务类型不存在");
        }
        taskType.setDeleted(true);
        taskType.update();
        return  ResultUtil.succeed();
    }


    public  ResultUtil update(Integer id,String name,Integer projectId){
        TaskType taskType=TaskType.dao.findById(id);
        if (taskType==null){
            throw new  OperationException("任务类型不存在");
        }
        if (Project.dao.findById(projectId)==null){
            throw  new OperationException("项目不存在");
        }
        if (TaskType.dao.find(SQL.SELECT_TASKTYPE_ID_NOT,name,projectId,id).size()!=0){
            throw  new  OperationException("该项目下任务类型已存在");
        }
        taskType.setName(name);
        taskType.setProjectId(Long.valueOf(projectId));
        taskType.update();
        return ResultUtil.succeed();
    }

    public List<TaskType>   getList(Integer projectId){
        List<TaskType> taskTypes=TaskType.dao.find(SQL.SELECT_TYPE_BY_GROUPID,projectId);
        return  taskTypes;
    }

}
