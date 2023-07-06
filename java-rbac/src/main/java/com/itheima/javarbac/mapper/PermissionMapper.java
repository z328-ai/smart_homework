package com.itheima.javarbac.mapper;

import com.itheima.javarbac.pojo.Permission;

import java.util.List;

public interface PermissionMapper {
    List<Permission> selectUserMenu(Integer userid);
}
