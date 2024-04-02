package com.business.model.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: GaoLu
 * @Date:2023-10-20 22:36
 * @Description: 分页出参父类
 */
@Data
public class PageVoEntity implements Serializable {

    @Schema(title = "总条数", description = "总条数", name = "total")
    private long total;
    @Schema(title = "条数", description = "一页多少条", name = "size")
    private long size;
    @Schema(title = "当前页", description = "当前第N页", name = "current")
    private long current;
    @Schema(title = "页", description = "共N页", name = "pages")
    private long pages;
}
