package com.itheima.javarbac.mapper;

import com.itheima.javarbac.pojo.Homework;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface HomeworkMapper {
    public abstract List<Homework> selectAllHomework();
    public abstract List<Homework> selectAllHomeworkById(Integer tid);
    public abstract Homework selectHomeworkById(@Param("tid") Integer tid,@Param("hid") Integer hid);
//    如果参数只有一个且是对象时可以直接#{属性}拿到属性值
    public abstract Integer updateHomeworkById(Homework homework);
//  布置作业(先插入作业，在查到刚插入作业id，在插入到老师和作业的关系表中)
    public abstract Integer insertHomework(Homework homework);
    public abstract Homework selectAddHomework(String name);
    public abstract Integer insertTeacherHomework(@Param("tid") Integer tid,@Param("hid") Integer hid);
//    删除作业（先通过id删除作业，在删除关联表的数据）
    public abstract Integer deleteHomework(Integer hid);
    public abstract Integer deleteTeacherHomework(Integer hid);
}
