package com.itheima.javarbac.mapper;

import com.itheima.javarbac.pojo.DoHomework;
import com.itheima.javarbac.pojo.Homework;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoHomeworkMapper {
    public abstract List<DoHomework> selectAllDoHomework(String homeworkName);

    public abstract Integer updateDoHomeworkStatus(@Param("status") Integer status,@Param("did") Integer did);

    public abstract DoHomework getAllReadyHomework(@Param("sid") Integer sid,@Param("homeworkName") String homeworkName);

    public abstract Integer insertDoHomework(Homework homework);

    public abstract Integer insertStudentTeacherDo(@Param("sid") Integer sid,@Param("homework") Homework homework);
}
