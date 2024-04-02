package com.business.model.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: GaoLu
 * @Date: 2023-10-20 17:47
 * @Description: 自动填充基础父类
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -922201555125882232L;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT) // 插入自动填充
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE) // 插入或更新时自动填充
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /** 创建人ID */
    private Long createId;

    /** 修改人ID */
    private Long updateId;

    /** 删除标记（0未删除，1已删除） */
    //    @TableLogic // 此注解表示该字段是逻辑删除字段（这里注掉是因为现用的mp版本是3.4.2，从3.3.0版本后就可以省略该注解）
    //private Integer delFlag;
}
