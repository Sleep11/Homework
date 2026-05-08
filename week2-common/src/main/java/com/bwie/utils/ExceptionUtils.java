package com.bwie.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 郜怀达
 * @version 1.0
 * @description: 将日志堆栈信息输入到文件
 * @date 2022/9/15 0:05
 */
public class ExceptionUtils {

    public static String getMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);

            // 将出错的栈信息输出到PrintWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if(sw != null) {
                try {
                    sw.close();
                } catch (IOException el) {
                    el.printStackTrace();
                }
            }
            if(pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
}
