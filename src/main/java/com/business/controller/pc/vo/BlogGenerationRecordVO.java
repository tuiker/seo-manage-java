package com.business.controller.pc.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(title = "博客生成记录 VO", description = "博客生成记录 VO")
public class BlogGenerationRecordVO {

    @Schema(title = "ID", description = "ID", name = "id")
    private Long id;

    @Schema(title = "博客Excel下载地址", description = "博客Excel下载地址", name = "downloadUrl")
    private String downloadUrl;

    @Schema(title = "创建时间", description = "创建时间", name = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
