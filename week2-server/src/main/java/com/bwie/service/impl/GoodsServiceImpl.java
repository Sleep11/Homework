package com.bwie.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bwie.domain.dto.QueryDto;
import com.bwie.domain.vo.GoodsVo;
import org.springframework.stereotype.Service;



import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bwie.mapper.GoodsMapper;
import com.bwie.domain.Goods;
import com.bwie.service.GoodsService;
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService{



    @Override
    public List<GoodsVo> goodsVoList(QueryDto queryDto) {
       // Page page = new Page(queryDto.getPageNum(),queryDto.getPageSize());
        return getBaseMapper().goodsVoList(queryDto);
    }

    @Override
    public com.baomidou.mybatisplus.extension.plugins.pagination.Page<GoodsVo> pageList(QueryDto queryDto) {
        Page<GoodsVo> newPage = new Page<>(queryDto.getPageNum(), queryDto.getPageSize());
        return getBaseMapper().pageList(newPage,queryDto);

    }
}
