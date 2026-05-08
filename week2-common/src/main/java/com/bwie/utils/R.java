package com.bwie.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : gaohuaida
 * @author : Mild-mannered soul programmer🎶
 * @date : 2025-02-25 15:31
 * @description :
 * 统一返回类型封装
 **/
@Data
public class R implements Serializable {

    //状态码  200 成功  500 失败
    private int code;

    //操作提示信息
    private String message;

    //操作标志位
    private boolean flag;

    //返回数据
    private Map<String,Object> data=new HashMap<String,Object>();


    /**
    * @Author: gaohuaida
    * @Description: 操作成功调用函数
    * @Param: []
    * @return: com.bwie.utils.R
    * @Date: 2025/3/6 13:37
    */
    public static R ok(){
        R r=new R();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setFlag(true);
        return r;
    }


    /**
    * @Author: gaohuaida
    * @Description: 操作失败调用函数
    * @Param: []
    * @return: com.bwie.utils.R
    * @Date: 2025/3/6 13:38
    */
    public static R error(){
        R r=new R();
        r.setCode(500);
        r.setMessage("操作失败");
        r.setFlag(false);
        return r;
    }


    /**
    * @Author: gaohuaida
    * @Description: 给返回数据重新赋值
    * @Param: [map]
    * @return: com.bwie.utils.R
    * @Date: 2025/3/6 13:38
    */
    public R data(Map<String,Object> map){
        this.data.putAll(map);
        return this;
    }

    /**
    * @Author: gaohuaida
    * @Description: 给返回数据重新赋值
    * @Param: [key, value]
    * @return: com.bwie.utils.R
    * @Date: 2025/3/6 13:38
    */
    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    /**
    * @Author: gaohuaida
    * @Description: 给操作消息重新赋值
    * @Param: [message]
    * @return: com.bwie.utils.R
    * @Date: 2025/3/6 13:39
    */
    public R message(String message){
        this.setMessage(message);
        return this;
    }

    /**
    * @Author: gaohuaida
    * @Description: 给状态码赋值
    * @Param: [code]
    * @return: com.bwie.utils.R
    * @Date: 2025/3/6 13:39
    */
    public R code(int code){
        this.setCode(code);
        return this;
    }

    /**
    * @Author: gaohuaida
    * @Description: 给操作标志位重新赋值
    * @Param: [flag]
    * @return: com.bwie.utils.R
    * @Date: 2025/3/6 13:39
    */
    public R flag(boolean flag){
        this.setFlag(flag);
        return this;
    }


}
