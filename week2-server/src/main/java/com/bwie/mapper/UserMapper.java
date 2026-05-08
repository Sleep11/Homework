package com.bwie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bwie.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}