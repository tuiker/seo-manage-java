package com.business.model.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: GaoLu
 * @Date:2023-10-20 22:36
 * @Description: 分页入参父类
 */
@Data
public class PageDaoEntity implements Serializable {
    //页
    @Schema(title = "页", description = "第几页，默认1", name = "page", defaultValue = "1", type = "Integer")
    private int page;
    @Schema(title = "条", description = "一页多少条", name = "pageSize", defaultValue = "10", type = "Integer")
    private int pageSize;
}
