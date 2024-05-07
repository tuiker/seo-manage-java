package com.business.controller.pc.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客数据实体
 * @Author yxf
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogData {

    @ExcelProperty(value = "title")
    @ColumnWidth(value = 20)
    private String title;

    @ExcelProperty(value = "content")
    @ColumnWidth(value = 120)
    private String content;

}
