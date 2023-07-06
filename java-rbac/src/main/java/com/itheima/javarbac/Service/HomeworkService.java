package com.itheima.javarbac.Service;

import com.itheima.javarbac.pojo.Homework;
import com.itheima.javarbac.pojo.Result;

public interface HomeworkService {
    public abstract Result selectAll(Integer currentPage,Integer size);

    public abstract Result selectAllHomework(Integer currentPage,Integer num);

    public abstract Result selectHomeWorkById(Integer hid);

    public abstract Result updateHomeworkById(Homework homework);

    public abstract Result addHomework(Homework homework);

    public abstract Result deleteHomework(Integer hid);




}
