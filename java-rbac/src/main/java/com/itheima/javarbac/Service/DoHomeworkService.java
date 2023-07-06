package com.itheima.javarbac.Service;

import com.itheima.javarbac.pojo.Homework;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.pojo.User;

public interface DoHomeworkService {
    public abstract Result selectAllDoHomework(Integer currentPage,Integer size,String homeworkName);

    public abstract Result updateDoHomeworkStatus(Integer status,Integer did);

    public abstract Result doHomework(Homework homework);
}
