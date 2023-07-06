package com.itheima.javarbac.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.javarbac.Service.DoHomeworkService;
import com.itheima.javarbac.mapper.DoHomeworkMapper;
import com.itheima.javarbac.pojo.DoHomework;
import com.itheima.javarbac.pojo.Homework;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoHomeworkServiceImpl implements DoHomeworkService {
    @Autowired
    private DoHomeworkMapper doHomeworkMapper;
    //分页查询所有做完的作业
    @Override
    public Result selectAllDoHomework(Integer currentPage,Integer size,String homeworkName) {
        PageHelper.startPage(currentPage,size);
        List<DoHomework> doHomeworks = doHomeworkMapper.selectAllDoHomework(homeworkName);
        PageInfo<DoHomework> doHomeworkPageInfo = new PageInfo<>(doHomeworks);
        List<DoHomework> list = doHomeworkPageInfo.getList();
        for(DoHomework doHomework : list){
            if(doHomework.getStatus()==1){
                doHomework.setStatus1(true);
            }else{
                doHomework.setStatus1(false);
            }
        }
        return Result.success(200,"展示"+homeworkName+"所有作业",doHomeworkPageInfo);
    }
    //修改做完作业的状态
    @Override
    public Result updateDoHomeworkStatus(Integer status,Integer did) {
        Integer integer = doHomeworkMapper.updateDoHomeworkStatus(status,did);
        if(integer == null){
            return Result.error("批改失败");
        }
        return Result.success(200,null);
    }
//做作业
    @Override
    @Transactional
    public Result doHomework(Homework homework) {
        doHomeworkMapper.insertDoHomework(homework);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        doHomeworkMapper.insertStudentTeacherDo(user.getId(),homework);
        return Result.success(200,"已提交");
    }
}
