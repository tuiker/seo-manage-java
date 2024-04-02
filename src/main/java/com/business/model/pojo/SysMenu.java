package com.business.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 菜单或按钮权限表
 * @Author yxf
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class SysMenu {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单或按钮名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 权限类型：1:目录，2：菜单，3：按钮
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 父级权限ID
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 菜单路由地址
     */
    @TableField(value = "path")
    private String path;

    /**
     * 组件路径
     */
    @TableField(value = "component")
    private String component;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 显示顺序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 是否隐藏：0：否，1：是
     */
    @TableField(value = "hidden")
    private Integer hidden;

}
