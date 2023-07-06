package com.itheima.javarbac.pojo;

import lombok.Data;

@Data
public class Permission {
    private Integer id;
    private String name;
    private String path;
    private String comment;
    private String icon;
}
