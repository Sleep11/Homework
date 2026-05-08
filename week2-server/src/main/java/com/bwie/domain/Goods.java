package com.bwie.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 商品编号
     */
    @TableField(value = "goods_no")
    private String goodsNo;

    /**
     * 商品名称
     */
    @TableField(value = "goods_name")
    private String goodsName;

    /**
     * 商品分类
     */
    @TableField(value = "goods_category")
    private String goodsCategory;

    /**
     * 库存数量
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 单位
     */
    @TableField(value = "unit")
    private String unit;

    /**
     * 库存状态
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 入库日期
     */
    @TableField(value = "create_date")
    private String createDate;

    /**
     * 最后更新时间
     */
    @TableField(value = "last_time")
    private String lastTime;
}