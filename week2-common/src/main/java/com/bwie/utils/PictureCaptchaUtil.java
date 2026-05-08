package com.bwie.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : gaohuaida
 * @author : Mild-mannered soul programmer🎶
 * @date : 2025-03-04 13:46
 * @description :
 * 图片验证码生成工具类
 **/
public class PictureCaptchaUtil {


    /**
    * @Author: gaohuaida
    * @Description: 4位随机数的图片验证码
    * @Param: [response]
    * @return: java.lang.String
    * @Date: 2025/3/4 14:08
    */
    public static String getRandomCaptcha(HttpServletResponse response) throws IOException {
        //生成图片验证码对象
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 60);
        lineCaptcha.setGenerator(randomGenerator);
        //获取验证码的值
        String code = lineCaptcha.getCode();
        //响应到浏览器
        lineCaptcha.write(response.getOutputStream());
        //返回生成的验证码
        return code;
    }

    /**
    * @Author: gaohuaida
    * @Description: 数学算术图片验证码
    * @Param: [response]
    * @return: java.lang.String
    * @Date: 2025/3/4 14:08
    */
    public static String getMathCaptcha(HttpServletResponse response) throws Exception {
        MathGenerator mathGenerator = new MathGenerator();
        //生成图片验证码对象
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 45,4,0);
        //设置验证码为数学验证码
        lineCaptcha.setGenerator(mathGenerator);
        //获取验证码的值
        String code = lineCaptcha.getCode();
        //获取验证码的计算结果
        Integer resultValue= (Integer) new ScriptEngineManager().getEngineByName("js").eval(code.substring(0,code.length()-1));
        //响应到浏览器
        lineCaptcha.write(response.getOutputStream());
        //返回生成的验证码
        return resultValue+"";
    }
}
