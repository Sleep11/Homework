package com.bwie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bwie.domain.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> myList(String userId);
}