package com.itheima.javarbac.controller;

import com.itheima.javarbac.Service.SysLogService;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.util.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;
    @GetMapping("/sysLog")
    @SysLog(operate = "查看系统日志")
    public Result selectLog(Integer currentPage,Integer size){
      return   sysLogService.selectLog(currentPage,size);
    }
}
