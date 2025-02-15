package com.ljz.compilationVSM.common.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel工具类
 *
 * @author ljz
 * @since 2025-02-15
 */
@Component
public class ExcelUtil {

    /**
     * 导出任意对象的列表为Excel文件
     *
     * @param dataList  数据列表
     * @param sheetName Excel sheet的名称
     * @param clazz     对应的对象类类型
     * @return 返回字节数组形式的Excel文件内容
     * @throws IOException io异常
     */
    public <T> byte[] exportToExcel(List<T> dataList, String sheetName, Class<T> clazz) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // 获取类的所有字段，包括私有字段
        Field[] fields = clazz.getDeclaredFields();

        // 创建表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true); // 设置字段可访问，避免访问private字段
            headerRow.createCell(i).setCellValue(field.getName()); // 使用字段名作为列头
        }

        // 空数据处理
        if (CollectionUtils.isEmpty(dataList)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            workbook.close();
            return byteArrayOutputStream.toByteArray();
        }

        // 填充数据
        int rowNum = 1;
        for (T data : dataList) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true); // 设置字段可访问
                Object value;
                try {
                    value = field.get(data); // 获取字段的值
                } catch (IllegalAccessException e) {
                    value = null; // 若无法访问，设置为null
                }
                row.createCell(i).setCellValue(value == null ? "" : value.toString()); // 填充单元格内容
            }
        }

        // 自动调整列宽
        for (int i = 0; i < fields.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 将数据写入字节流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 解析上传的Excel文件并将数据转换成对象列表
     *
     * @param fileBytes 上传的Excel文件字节数组
     * @param clazz     对应的对象类类型
     * @param <T>       目标类类型
     * @return 返回解析后的对象列表
     * @throws IOException io异常
     */
    public <T> List<T> importFromExcel(byte[] fileBytes, Class<T> clazz) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<T> dataList = new ArrayList<>();

        // 获取Excel文件的输入流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);
        Workbook workbook = new XSSFWorkbook(byteArrayInputStream);
        Sheet sheet = workbook.getSheetAt(0); // 获取第一个sheet
        if (sheet == null) {
            workbook.close();
            return dataList; // 如果sheet为空，直接返回空列表
        }

        // 获取类的所有字段
        Field[] fields = clazz.getDeclaredFields();

        // 获取第一行作为字段名称
        Row headerRow = sheet.getRow(0); // 第一行数据
        if (headerRow == null) {
            workbook.close();
            return dataList; // 如果没有第一行数据，直接返回空列表
        }

        // 获取字段名称的顺序
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }

        // 创建列索引映射（列名 -> 列索引）
        Map<String, Integer> headerMap = new HashMap<>();
        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null && cell.getCellType() == CellType.STRING) {
                String headerName = cell.getStringCellValue();
                headerMap.put(headerName, i); // 保存表头名称与列索引的映射关系
            }
        }

        // 遍历sheet的所有行
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum); // 获取每一行数据
            if (row == null)
                continue;

            T obj = clazz.getDeclaredConstructor().newInstance(); // 创建对象实例

            // 遍历字段并填充对象
            for (String fieldName : fieldNames) {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true); // 设置字段可访问

                // 检查表头中是否存在当前字段名，如果存在则获取列的值
                if (headerMap.containsKey(fieldName)) {
                    int columnIndex = headerMap.get(fieldName); // 获取对应的列索引
                    Cell cell = row.getCell(columnIndex); // 获取当前单元格
                    if (cell != null) {
                        // 处理不同数据类型并统一为字符串
                        String value = getCellValue(cell);
                        if (value != null) {
                            field.set(obj, value); // 将值设置到对象的字段
                        }
                    }
                }
            }

            dataList.add(obj); // 将对象添加到列表中
        }

        workbook.close();
        return dataList;
    }


    /**
     * 获取Excel单元格的值并转化为字符串
     *
     * @param cell Excel单元格
     * @return 返回单元格的字符串值
     */
    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // 日期转化为字符串
                } else {
                    return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()); // 布尔值转化为字符串
            case FORMULA:
                return cell.getCellFormula(); // 公式转化为字符串
            default:
                return ""; // 返回空字符串
        }
    }

}
