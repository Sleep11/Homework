package com.bwie.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,12}$",message ="密码由数字和字母组成,6到12位")
    @TableField(value = "`password`")
    private String password;
}