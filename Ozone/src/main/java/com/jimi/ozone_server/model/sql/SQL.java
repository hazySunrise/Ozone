package com.jimi.ozone_server.model.sql;

public class SQL {
    public  static final String SELECT_PROJECT_BY_NAME="select * from  project where name= ? and deleted=0";

    public  static final String SELECT_GROUP_BY_NAME="select * from `group` where name=? and deleted=0";

    public  static  final String SELECT_TASK_BY_TYPEID_AND_NAME="select * from task where name=? and type_id=?";

    public  static  final String SELECT_TASK_ID_NOT="select * from task where name=? and type_id=? and id!=?";

    public  static final String  COUNT_CONFLICT_SQL="select count(*) as quantity from task_schedule t where(\n" +
            "(t.begin_time>=? and t.begin_time<=?) or (t.begin_time<= ? and t.end_time>=?) or (t.end_time>=? and t.end_time<=?) or (t.begin_time>=? and t.end_time<=?))and t.staff_id=? and t.finish_time is   null and  t.deleted=0";

    public static final String SELECT_SCHEDULE_BY_TASID="delete from task_schedule where task_id=?";

    public static  final  String DELETE_PERTASK="delete from `pre_task` WHERE task_id=? ";

    public  static final String SELECT_ALL_TASK_BY_PROJECTID="SELECT id,name from task where type_id IN( SELECT id from task_type  WHERE project_id=? and deleted=0) and deleted=0";

    public  static  final  String SELECT_TASKTYPE_BY_NAME_AND_PROJECTID="select * from  task_type where name=? and project_id=? and deleted=0";

    public  static  final  String SELECT_TASKTYPE_ID_NOT="select * from  task_type where name=? and project_id=? and deleted=0 and id!=?";

    public static  final String   SELECT_ALL_PROJECT="select id,name from `project` WHERE deleted=0";

    public static  final String   SELECT_ALL_GROUP="select id,name from `group` WHERE deleted=0";

    public  static  final String SELECT_STAFF_BY_GROUPID="select id,name from `staff` WHERE group_id=? AND deleted=0";

    public  static  final String SELECT_STAFF_BY_ID="select id,name from `staff` WHERE id=? ";

    public  static  final String SELECT_TYPE_BY_GROUPID="select id,name from task_type WHERE project_id=? AND deleted=0";

    public static  final String SELECT_SCHEDULE_BY_STAFFID="select * from task_schedule a join task t on  t.id=a.task_id join project p on  p.id= a.project_id where a.staff_id =?";

    public static  final String SELECT_SCHEDULE_BY_TASKID="select * from task_schedule ts  where ts.task_id=? and ts.deleted=0 ";

    public  static final String SELECT_TASK_BY_TYPEID="select id,name from task where type_id=? and deleted=0";

    public static  final String  SELECT_PRETASK_BY_TASKID="select id,pre_task from pre_task where task_id=? and deleted=0";
}
