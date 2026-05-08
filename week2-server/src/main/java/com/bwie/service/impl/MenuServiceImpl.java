package com.bwie.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bwie.domain.Menu;
import com.bwie.mapper.MenuMapper;
import com.bwie.service.MenuService;
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService{

    @Override
    public List<Menu> myList(String userId) {
        return getBaseMapper().myList(userId);
    }
}
