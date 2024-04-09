package com.business.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EasyExcelUtil {

    /**
     * 获取Excel中指定列的数据并返回集合
     * @param filePath 文件地址
     * @param sheetNo Sheet的编码,使用这个来指定读取哪个Sheet
     * @param columnIndex 列的编码，使用这个指定读取那一列的数据
     * @param dataType 数据类型
     * @param <T>
     * @return
     */
    public static <T> List<T> readColumnValues(String filePath, int sheetNo , int columnIndex, Class<T> dataType) {
        List<T> columnValues = new ArrayList<>();

        BufferedInputStream inputStream = FileUtil.getInputStream(filePath);
        EasyExcel.read(inputStream, new AnalysisEventListener<Map<Integer,String>>() {
            @Override
            public void invoke(Map<Integer,String> data, AnalysisContext context) {
                String value = data.get(columnIndex);
                if (StrUtil.isNotEmpty(value)){
                    T convertedValue = convertValue(value, dataType);
                    columnValues.add(convertedValue);
                }
            }

            @Override
            public void onException(Exception exception, AnalysisContext context) throws Exception {
                throw new RuntimeException("读取Excel异常!");
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet(sheetNo).doRead();
        return columnValues;
    }

    private static <T> T convertValue(String value, Class<T> dataType) {
        if (dataType.equals(String.class)) {
            return dataType.cast(value);
        } else if (dataType.equals(Integer.class)) {
            return dataType.cast(Integer.parseInt(value));
        } else if (dataType.equals(Double.class)) {
            return dataType.cast(Double.parseDouble(value));
        }else {
            // 其他数据类型的转换逻辑
            return null;
        }
    }

}
