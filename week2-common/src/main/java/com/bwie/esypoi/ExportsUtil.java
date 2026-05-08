package com.bwie.esypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * EasyPoi 导出工具类（SpringBoot 2.3.7 专用）
 * 修复：乱码、流异常、空指针、重复创建样式、资源泄漏
 * @author Monte
 */
public class ExportsUtil {

    /**
     * 普通导出
     */
    public static <T> void exports(HttpServletResponse response, Class<?> classEntity, List<T> data,
                                   String sheetName, String title, String fileName) {
        Workbook workbook = null;
        try {
            ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
            workbook = ExcelExportUtil.exportExcel(exportParams, classEntity, data);

            // 设置响应头（SpringBoot 标准写法）
            setResponseHeader(response, fileName);

            // 写出
            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 安全关闭资源
            close(workbook, response);
        }
    }

    /**
     * 统一样式导出
     */
    public static <T> void exportsStyle(HttpServletResponse response, Class<?> classEntity, List<T> data,
                                        String sheetName, String title, String fileName,
                                        List<Integer[]> stylePointList, HashMap<String, Object> styleMap) {
        Workbook workbook = null;
        try {
            ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
            workbook = ExcelExportUtil.exportExcel(exportParams, classEntity, data);

            // 创建统一样式
            CellStyle cellStyle = createCellStyle(workbook, styleMap);

            // 应用样式
            Sheet sheet = workbook.getSheet(sheetName);
            for (Integer[] point : stylePointList) {
                int rowNum = point[0];
                int cellNum = point[1];
                Row row = sheet.getRow(rowNum);
                if (row != null) {
                    Cell cell = row.getCell(cellNum);
                    if (cell != null) {
                        cell.setCellStyle(cellStyle);
                    }
                }
            }

            setResponseHeader(response, fileName);
            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(workbook, response);
        }
    }

    /**
     * 多单元格不同样式导出
     */
    public static <T> void exportsStyle(HttpServletResponse response, Class<?> classEntity, List<T> data,
                                        String sheetName, String title, String fileName,
                                        List<Integer[]> stylePointList, List<HashMap<String, Object>> styleMapList) {
        Workbook workbook = null;
        try {
            ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
            workbook = ExcelExportUtil.exportExcel(exportParams, classEntity, data);

            Sheet sheet = workbook.getSheet(sheetName);
            int i = 0;
            for (Integer[] point : stylePointList) {
                if (i >= styleMapList.size()) {
                    break;
                }
                HashMap<String, Object> styleMap = styleMapList.get(i);
                CellStyle cellStyle = createCellStyle(workbook, styleMap);

                int rowNum = point[0];
                int cellNum = point[1];
                Row row = sheet.getRow(rowNum);
                if (row != null) {
                    Cell cell = row.getCell(cellNum);
                    if (cell != null) {
                        cell.setCellStyle(cellStyle);
                    }
                }
                i++;
            }

            setResponseHeader(response, fileName);
            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(workbook, response);
        }
    }

    // ====================== 以下是抽取的公共方法 ======================

    /**
     * 设置 SpringBoot 下载响应头（解决乱码）
     */
    private static void setResponseHeader(HttpServletResponse response, String fileName) throws IOException {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=UTF-8''" + fileName + ".xlsx");
    }

    /**
     * 创建单元格样式（抽取复用，避免重复创建）
     */
    private static CellStyle createCellStyle(Workbook workbook, HashMap<String, Object> styleMap) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        if (styleMap == null) {
            return cellStyle;
        }
        // 字体颜色
        if (styleMap.get("color") != null) {
            Font font = workbook.createFont();
            font.setColor((short) styleMap.get("color"));
            cellStyle.setFont(font);
        }
        // 背景色
        if (styleMap.get("background") != null) {
            cellStyle.setFillForegroundColor((short) styleMap.get("background"));
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        return cellStyle;
    }

    /**
     * 安全关闭资源
     */
    private static void close(Workbook workbook, HttpServletResponse response) {
        try {
            if (workbook != null) {
                workbook.close();
            }
            if (response != null && response.getOutputStream() != null) {
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}