package com.jimi.ozone_server.config;

import com.jfinal.config.*;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.jimi.ozone_server.controller.*;
import com.jimi.ozone_server.interceptor.ActionLogInterceptor;
import com.jimi.ozone_server.interceptor.CORSInterceptor;
import com.jimi.ozone_server.interceptor.ErrorLogInterceptor;
import com.jimi.ozone_server.model.MappingKit;
import com.jimi.ozone_server.util.TokenBox;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import java.sql.Connection;
import java.sql.SQLException;

public class OzoneConfig extends JFinalConfig {
    private Level level;
    private static DruidPlugin dbPlugin;

    @Override
    public void configConstant(Constants me) {
        // 设置当前是否为开发模式
        me.setDevMode(true);
        // 开启依赖注入
        me.setInjectDependency(true);
        me.setJsonFactory(new MixedJsonFactory());
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/group", GroupController.class);
        me.add("/project", ProjectController.class);
        me.add("/staff", StaffController.class);
        me.add("/task", TaskController.class);
        me.add("/type", TaskTypeController.class);
        me.add("/display",DisplayController.class);
    }

    @Override
    public void configEngine(Engine me) {

    }

    @Override
    public void configPlugin(Plugins me) {
        PropKit.use("properties.ini");
        //设置关闭日志
        Configurator.setRootLevel(Level.OFF);
        //判断环境来设置数据源和日志等级
        dbPlugin=null;
        level=null;
        // 配置数据库连接池插件
        dbPlugin= new DruidPlugin(PropKit.get("d_url"), PropKit.get("d_user"), PropKit.get("d_password"));
        level =Level.DEBUG;
        // orm映射 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dbPlugin);
        arp.setShowSql(false);
        arp.setDialect(new MysqlDialect());
        // dbPlugin.setDriverClass("com.mysql.cj.jdbc.Driver");
        dbPlugin.setDriverClass("com.mysql.jdbc.Driver");
        /********在此添加数据库 表-Model 映射*********/
        // 如果使用了JFinal Model 生成器 生成了BaseModel 把下面注释解开即可
        MappingKit.mapping(arp);
        // 添加到插件列表中
        me.add(dbPlugin);
        me.add(arp);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.addGlobalActionInterceptor(new ActionLogInterceptor());
        me.addGlobalActionInterceptor(new CORSInterceptor());
        me.addGlobalActionInterceptor(new ErrorLogInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {

    }

    @Override
    public void beforeJFinalStop() {
        TokenBox.stop();
    }

    @Override
    public void afterJFinalStart() {
        TokenBox.start(48);
        //设置日志等级
        Configurator.setRootLevel(level);
    }

    public static Connection getLog4j2JDBCAppenderConnection() throws SQLException {
        return dbPlugin.getDataSource().getConnection();
    }
}
