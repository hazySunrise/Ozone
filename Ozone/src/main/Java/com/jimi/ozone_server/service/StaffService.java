package com.jimi.ozone_server.service;

import com.jimi.ozone_server.exception.OperationException;
import com.jimi.ozone_server.model.Group;
import com.jimi.ozone_server.model.Staff;
import com.jimi.ozone_server.model.sql.SQL;
import com.jimi.ozone_server.util.ResultUtil;

import java.util.List;

/*
 * 员工管理逻辑层
 */
public class StaffService {

    public ResultUtil add(String name,Integer groupId){
        if (Group.dao.findById(groupId)==null){
            throw new OperationException("小组不存在");
        }
        Staff staff=new Staff();
        staff.setDeleted(false);
        staff.setGroupId(Long.valueOf(groupId));
        staff.setName(name);
        staff.save();
        return  ResultUtil.succeed();
    }


    public  ResultUtil delete(Integer id){
        Staff staff=Staff.dao.findById(id);
        if (staff==null){
            throw new  OperationException("该员工不存在");
        }
        staff.setDeleted(true);
        staff.update();
        return  ResultUtil.succeed();
    }

    public ResultUtil update(Integer id,String name,Integer groupId){
        Staff staff=Staff.dao.findById(id);
        if (staff==null){
            throw new  OperationException("该员工不存在");
        }
        if (Group.dao.findById(groupId)==null){
            throw new OperationException("小组不存在");
        }
        staff.setName(name);
        staff.setGroupId(Long.valueOf(groupId));
        staff.update();
        return  ResultUtil.succeed();
    }

    public List<Staff> getStaff(Integer groupId){
        if (Group.dao.findById(groupId)==null){
            throw new OperationException("小组不存在");
        }
       List<Staff> staffs=Staff.dao.find(SQL.SELECT_STAFF_BY_GROUPID,groupId);
        return staffs;
    }

}
