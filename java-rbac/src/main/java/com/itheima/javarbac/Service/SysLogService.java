package com.itheima.javarbac.Service;

import com.itheima.javarbac.pojo.Result;

public interface SysLogService {
    public abstract Result selectLog(Integer currentPage,Integer size);
}
