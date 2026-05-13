package com.bwie.domain.dto;

import lombok.Data;

/**
 * @author wangyuxiang
 * @description: TODO
 * @since 2026/5/13 17:28:00
 */
@Data
public class PageDto {
    private Integer pageNum=1;
    private Integer pageSize=3;
}