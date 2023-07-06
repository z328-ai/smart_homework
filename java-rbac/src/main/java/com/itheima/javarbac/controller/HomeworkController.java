package com.itheima.javarbac.controller;

import com.itheima.javarbac.Service.HomeworkService;
import com.itheima.javarbac.pojo.Homework;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.util.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homework")
public class HomeworkController {
    @Autowired
    private HomeworkService homeworkService;
    @GetMapping("/getAll")
    @SysLog(operate = "查看布置的作业")
    public Result getAllHomework(Integer currentPage,Integer size){
       return homeworkService.selectAllHomework(currentPage,size);
    }

    @GetMapping("/getSingleById")
    @SysLog(operate = "查看作业提交情况")
    public Result getSingleById(Integer hid){
        return homeworkService.selectHomeWorkById(hid);
    }

    @PutMapping("/updateHomework")
    @SysLog(operate = "更新作业")
    public Result updateHomework(@RequestBody Homework homework){
        return homeworkService.updateHomeworkById(homework);
    }

    @PostMapping("addHomework")
    @SysLog(operate = "布置作业")
    public Result addHomework(@RequestBody Homework homework){
        return homeworkService.addHomework(homework);
    }

    @DeleteMapping("/deleteHomework")
    @SysLog(operate = "删除作业")
    public Result deleteHomework(Integer hid){
        return homeworkService.deleteHomework(hid);
    }
    //查询所有老师布置的作业
    @GetMapping("/getAllHomework")
    @SysLog(operate = "分页查询所有的作业")
    public Result getAllHomeworks(Integer currentPage,Integer size){
        return homeworkService.selectAll(currentPage,size);
    }
}
