package com.bwie.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bwie.domain.Goods;
import com.bwie.domain.dto.PageDto;
import com.bwie.domain.dto.QueryDto;
import com.bwie.domain.vo.GoodsVo;
import com.bwie.service.GoodsService;
import com.bwie.utils.R;
import io.swagger.annotations.Api;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    RedissonClient redissonClient;

    @PostMapping("list")
    public R list(@RequestBody QueryDto queryDto, PageDto pageDto) {
        Page<GoodsVo> list = goodsService.pageList(queryDto,pageDto);
        return R.ok().data("list", list);
    }


    @PostMapping("addGoods")
    public R addGoods(@RequestBody Goods goods) {

        String key = "lockKey:" + goods.getGoodsName();

        RLock lock = redissonClient.getLock(key);

        try {
            boolean getLock = lock.tryLock(3, 5, TimeUnit.SECONDS);

            if (!getLock) {
                return R.error().message("请勿重复提交");
            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        goods.setLastTime(DateUtil.now());
        boolean save = goodsService.save(goods);
        if (!save) {
            return R.error().message("新增失败");
        }
        return R.ok();
    }
}