package com.business.controller.pc.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 替换文章关键词后导出的数据实体
 * @Author yxf
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplaceExportData {

    /** 经度 */
    @ExcelProperty(value = "longitude")
    @ColumnWidth(value = 20)
    private String longitude;

    /** 纬度 */
    @ExcelProperty(value = "latitude")
    @ColumnWidth(value = 20)
    private String latitude;

    /** 标题 */
    @ExcelProperty(value = "title")
    @ColumnWidth(value = 50)
    private String title;

    /** 描述 */
    @ExcelProperty(value = "describe")
    @ColumnWidth(value = 100)
    private String describe;

}
