package com.itheima.javarbac.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.javarbac.Service.SysLogService;
import com.itheima.javarbac.mapper.LogMapper;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.pojo.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private LogMapper logMapper;
    @Override
    public Result selectLog(Integer currentPage, Integer size) {
        PageHelper.startPage(currentPage,size);
        List<SysLog> sysLogs = logMapper.selectLog();
        PageInfo<SysLog> sysLogPageInfo = new PageInfo<>(sysLogs);
        return Result.success(200,null,sysLogPageInfo);
    }
}
