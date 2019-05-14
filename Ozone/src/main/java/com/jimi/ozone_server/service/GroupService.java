package com.jimi.ozone_server.service;

import com.jimi.ozone_server.exception.OperationException;
import com.jimi.ozone_server.model.Group;
import com.jimi.ozone_server.model.sql.SQL;
import com.jimi.ozone_server.util.ResultFactory;

import java.util.List;


/*
 * 小组管理逻辑层
 */
public class GroupService {

    public ResultUtil delete(Integer id){
        Group group =Group.dao.findById(id);
        if (group==null){
            throw new OperationException("小组不存在");
        }
        group.setDeleted(true);
        group.update();
        return  ResultUtil.succeed();
    }

    public ResultUtil add(String name){
        if (Group.dao.find(SQL.SELECT_GROUP_BY_NAME,name).size()!=0){
            throw new  OperationException("小组已存在");
        }
        Group group=new Group();
        group.setName(name);
        group.setDeleted(false);
        group.save();
        return ResultUtil.succeed();
    }

    public ResultUtil update(Integer id,String name){
        Group group=Group.dao.findById(id);
        if (group==null){
            throw new  OperationException("小组不存在");
        }
        if (Group.dao.find(SQL.SELECT_GROUP_BY_NAME,name).size()!=0){
            throw new  OperationException("小组已存在");
        }
        group.setName(name);
        group.update();
        return  ResultUtil.succeed();
    }

    public List<Group> getList(){
       List<Group> groups=Group.dao.find(SQL.SELECT_ALL_GROUP);
       return groups;
    }
}
