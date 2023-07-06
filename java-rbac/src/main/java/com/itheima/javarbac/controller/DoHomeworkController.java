package com.itheima.javarbac.controller;

import com.itheima.javarbac.Service.DoHomeworkService;
import com.itheima.javarbac.pojo.Homework;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.util.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doHomework")
public class DoHomeworkController {
    @Autowired
    private DoHomeworkService doHomeworkService;
    @GetMapping("/getAll")
    @SysLog(operate = "分页查询布置的所有作业")
    public Result getAllDoHomework(Integer currentPage,Integer size,String homeworkName){
      return doHomeworkService.selectAllDoHomework(currentPage,size,homeworkName);
    }
    @GetMapping("/updateDoHomeworkStatus")
    @SysLog(operate = "批改作业")
    public Result updateDoHomeworkStatus(Integer status,Integer did){
        return doHomeworkService.updateDoHomeworkStatus(status,did);
    }
    @PostMapping("/DoHomework")
    @SysLog(operate = "做作业")
    public Result DoHomework(@RequestBody Homework homework){
        System.out.println(homework);
        return doHomeworkService.doHomework(homework);
    }
}
