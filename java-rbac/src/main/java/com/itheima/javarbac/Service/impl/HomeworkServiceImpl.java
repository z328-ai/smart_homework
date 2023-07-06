package com.itheima.javarbac.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.javarbac.Service.HomeworkService;
import com.itheima.javarbac.mapper.DoHomeworkMapper;
import com.itheima.javarbac.mapper.HomeworkMapper;
import com.itheima.javarbac.pojo.DoHomework;
import com.itheima.javarbac.pojo.Homework;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HomeworkServiceImpl implements HomeworkService {
    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private DoHomeworkMapper doHomeworkMapper;
//    查询所有老师布置的作业在查询学生做完的作业进行整合
    @Override
    public Result selectAll(Integer currentPage,Integer size) {
       
        PageHelper.startPage(currentPage,size);
        //    查询所有老师布置的作业
        List<Homework> homeworks = homeworkMapper.selectAllHomework();
        for (Homework e : homeworks) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            DoHomework allReadyHomework = doHomeworkMapper.getAllReadyHomework(user.getId(), e.getName());
//            如果在提交作业列表无法通过作业名和学生id查到数据，说明未写改作业
            if(allReadyHomework==null){
                e.setStatus(0);
            }else{
                e.setContent(allReadyHomework.getContent());
                e.setStatus(allReadyHomework.getStatus());
            }
        }
        PageInfo<Homework> homeworkPageInfo = new PageInfo<>(homeworks);
        return Result.success(200,null,homeworkPageInfo);
    }

    //    传入老师id，得到该老师的所有作业
    @Override
    public Result selectAllHomework(Integer currentPage,Integer size) {
        PageHelper.startPage(currentPage,size);
        //拿到目前用户的信息
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Homework> homework = homeworkMapper.selectAllHomeworkById(user.getId());
        PageInfo<Homework> homeworkPageInfo = new PageInfo<>(homework);
        return Result.success(200,null,homeworkPageInfo);
    }
//    传入老师id和作业id,得到该老师布置的某个作业
    @Override
    public Result selectHomeWorkById(Integer hid) {
        //拿到目前用户的信息
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Homework homework = homeworkMapper.selectHomeworkById(user.getId(), hid);
        if(homework==null){
            return Result.error("数据回显失败");
        }
        return Result.success(200,null,homework);
    }
//    作业通过id进行修改
    @Override
    public Result updateHomeworkById(Homework homework) {
        Integer integer = homeworkMapper.updateHomeworkById(homework);
        if(integer==null){
            return Result.error("修改作业失败");
        }
        return Result.success(200,"修改成功");
    }
//    添加作业
    @Override
    @Transactional
    public Result addHomework(Homework homework) {
        //插入作业
        homeworkMapper.insertHomework(homework);
        //查询刚插入的作业
        Homework addHomework = homeworkMapper.selectAddHomework(homework.getName());
        //拿到目前用户的信息
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //插入老师和作业的关联表
        homeworkMapper.insertTeacherHomework(user.getId(), addHomework.getId());
        return Result.success(200,"布置作业成功");
    }
//    删除作业
    @Override
    public Result deleteHomework(Integer hid) {
        //通过id删除作业
        homeworkMapper.deleteHomework(hid);
        //删除老师和作业关联表数据
        homeworkMapper.deleteTeacherHomework(hid);
        return Result.success(200,"删除作业成功");
    }
}
