package com.bwie.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangyuxiang
 * @description: TODO
 * @since 2026/5/8 11:25:17
 */
@Data
public class QueryDto implements Serializable  {
    /**
     * 商品编号
     */
    private String goodsNo;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 入库时间区间
     */
    private String start;
    private String end;

    private Integer pageNum=1;
    private Integer pageSize=3;


}