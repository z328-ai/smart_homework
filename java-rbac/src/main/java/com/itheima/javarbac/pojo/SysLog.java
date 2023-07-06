package com.itheima.javarbac.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysLog {
    private Integer id;
    private String username;
    private String operate;
    private String createTime;
    private Long spendTime;
    private String role;
}
