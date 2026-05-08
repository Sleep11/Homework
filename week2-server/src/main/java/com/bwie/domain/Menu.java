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
@TableName(value = "t_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 权限名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 访问路径
     */
    @TableField(value = "`path`")
    private String path;

    /**
     * 父级id
     */
    @TableField(value = "parent_id")
    private Integer parentId;
}