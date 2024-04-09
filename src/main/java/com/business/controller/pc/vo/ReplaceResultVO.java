package com.business.controller.pc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "批量替换结果 VO", description = "批量替换结果 VO")
public class ReplaceResultVO {

    @Schema(title = "数据总条数", description = "数据总条数", name = "total")
    private Integer total;

    @Schema(title = "文件下载地址", description = "文件下载地址", name = "fileDownloadPath")
    private String fileDownloadPath;

}
