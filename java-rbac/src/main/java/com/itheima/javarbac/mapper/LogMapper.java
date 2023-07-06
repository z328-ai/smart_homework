package com.itheima.javarbac.mapper;

import com.itheima.javarbac.pojo.SysLog;

import java.util.List;

public interface LogMapper {
    public abstract Integer insertLog(SysLog sysLog);

    public abstract List<SysLog> selectLog();
}
