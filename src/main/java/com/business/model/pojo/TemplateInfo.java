package com.business.model.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.business.common.vo.PageResult;
import com.business.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("template_info")
@Schema(title = "模板信息实体类")
public class TemplateInfo extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 模板标题 */
    private String templateTitle;

    /** 模板描述 */
    private String templateDesc;

    /** 模板关键词 */
    private String templateKeyword;

}
