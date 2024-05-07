package com.business.model.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.business.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("blog_generation_record")
@Schema(title = "博客生成记录实体类")
public class BlogGenerationRecord{

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 博客Excel下载地址 */
    private String downloadUrl;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT) // 插入自动填充
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 创建人ID */
    private Long createId;

}
