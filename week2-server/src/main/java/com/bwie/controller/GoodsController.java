package com.bwie.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bwie.domain.Goods;
import com.bwie.domain.dto.QueryDto;
import com.bwie.domain.vo.GoodsVo;
import com.bwie.service.GoodsService;
import com.bwie.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangyuxiang
 * @description: TODO
 * @since 2026/5/8 11:05:11
 */
@Api(tags = "库存列表")
@RestController
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @PostMapping("list")
    public R list(@RequestBody QueryDto queryDto){
        List<GoodsVo> goodsVoList = goodsService.goodsVoList(queryDto);
        Page<GoodsVo> list = goodsService.pageList(queryDto);
        return R.ok().data("list",list);
    }


    @PostMapping("addGoods")
    public R addGoods(@RequestBody Goods goods){
        goods.setLastTime(DateUtil.now());
        boolean save = goodsService.save(goods);
        if (!save){
            return R.error().message("新增失败");
        }
        return R.ok();
    }
}