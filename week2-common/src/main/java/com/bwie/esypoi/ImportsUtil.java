package com.bwie.esypoi;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import java.io.File;
import java.util.List;

/**
 * @author Monte
 * @author 644527030@qq.com
 * @author 知识改变命运，代码改变世界！
 * @date 2024年07月30日    15:02
 * @PackageName:com.bw.utils.esypoi
 * @ClassName: ImportsUtil
 * @Description HomeController
 */


public class ImportsUtil {

    /**
     * 基本的导入
     * @param filePath 文件的路径
     * @param pojoClass
     * @param titleRow
     * @param headRow
     * @return
     * @param <T>
     */
    public static <T> List<T> imports(String filePath, Class<?> pojoClass, Integer titleRow, Integer headRow){

        // 把要导入的内容转换为file
        File file = new File(filePath);

        // 构造导入参数
        ImportParams importParams = new ImportParams();

        // 设置标题行数
        importParams.setTitleRows(titleRow != null?titleRow:1);

        // 设置表头行数
        importParams.setHeadRows(headRow != null?headRow:1);

        // 获取excel数据为对象并且保存到list中
        List<T> studentEntityList = ExcelImportUtil.importExcel(file, pojoClass, importParams);

        return studentEntityList;
    }

    /**
     * 基本的导入
     * @param file 文件的file
     * @param pojoClass
     * @param titleRow
     * @param headRow
     * @return
     * @param <T>
     */
    public static <T> List<T> imports(File file, Class<?> pojoClass, int titleRow, int headRow){

        // 构造导入参数
        ImportParams importParams = new ImportParams();

        // 设置标题行数
        importParams.setTitleRows(1);

        // 设置表头行数
        importParams.setHeadRows(1);

        // 获取excel数据为对象并且保存到list中
        List<T> studentEntityList = ExcelImportUtil.importExcel(file, pojoClass, importParams);

        return studentEntityList;
    }
}
