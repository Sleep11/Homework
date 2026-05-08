package com.bwie.domain.vo;

import com.bwie.domain.Goods;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangyuxiang
 * @description: TODO
 * @since 2026/5/8 11:31:38
 */
@Data
public class GoodsVo extends Goods implements Serializable {
    private String dictName;
}