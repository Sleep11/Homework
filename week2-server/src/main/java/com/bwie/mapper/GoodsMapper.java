package com.bwie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bwie.domain.Goods;
import com.bwie.domain.dto.QueryDto;
import com.bwie.domain.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {


    List<GoodsVo> goodsVoList( QueryDto queryDto);

    Page<GoodsVo> pageList(Page newPage, @Param("queryDto") QueryDto queryDto);
}