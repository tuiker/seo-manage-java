package com.business.controller.pc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "模板信息 VO", description = "模板信息 VO")
public class TemplateInfoVO {

    @Schema(title = "主键ID", description = "主键ID", name = "id")
    private long id;

    @Schema(title = "模板标题", description = "模板标题", name = "templateTitle")
    private String templateTitle;

    @Schema(title = "模板描述", description = "模板描述", name = "templateDesc")
    private String templateDesc;

    @Schema(title = "模板关键词", description = "模板关键词", name = "templateKeyword")
    private String templateKeyword;

    @Schema(title = "替换关键字并生成的Excel中的行数", description = "替换关键字并生成的Excel中的行数", name = "total")
    private Integer total;

    @Schema(title = "替换关键字并生成的Excel文件路径", description = "替换关键字并生成的Excel文件路径", name = "filePath")
    private String filePath;
}
