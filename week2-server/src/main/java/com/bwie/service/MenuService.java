package com.bwie.service;

import com.bwie.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MenuService extends IService<Menu>{


    List<Menu> myList(String userId);
}
