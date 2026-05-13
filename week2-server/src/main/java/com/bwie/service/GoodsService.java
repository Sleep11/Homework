package com.bwie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bwie.domain.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bwie.domain.dto.PageDto;
import com.bwie.domain.dto.QueryDto;
import com.bwie.domain.vo.GoodsVo;

import java.util.List;

public interface GoodsService extends IService<Goods>{




    List<GoodsVo> goodsVoList(QueryDto queryDto);

    Page<GoodsVo> pageList(QueryDto queryDto, PageDto pageDto);
}
