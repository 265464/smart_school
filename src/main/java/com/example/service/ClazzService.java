package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Clazz;

public interface ClazzService extends IService<Clazz> {
    IPage getClazzByOpr(Page<Clazz> page, Clazz clazz);
}
