package com.business.controller.pc.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: GaoLu
 * @Date: 2023-10-18 17:53
 * @Description: 用户信息返回类
 */
@Data
@Schema(title = "用户信息", description = "用户信息对象")
public class UserInfoVO {
    @Schema(title = "用户id", description = "用户id", name = "id")
    private long id;

    @Schema(title = "用户账号", description = "用户账号", name = "userName")
    private String userName;

    @Schema(title = "头像", description = "用户头像", name = "userImg")
    private String userImg;

    @Schema(title = "最近登录时间", description = "最近登录时间", name = "recentLoginTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recentLoginTime;

    @Schema(title = "创建时间", description = "创建时间", name = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(title = "修改时间", description = "修改时间", name = "updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(title = "角色ID", description = "角色ID", name = "roleId")
    private Integer roleId;

    @Schema(title = "创建人", description = "创建人", name = "creator")
    private String creator;

    @Schema(title = "修改人", description = "修改人", name = "updater")
    private String updater;

    @Schema(title = "角色名称", description = "角色名称", name = "roleName")
    private String roleName;
}
