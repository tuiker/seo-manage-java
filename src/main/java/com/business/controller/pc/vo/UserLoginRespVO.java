package com.business.controller.pc.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 用户登录成功 响应 VO
 * @Author yxf
 */
@Data
@Schema(title ="用户登录成功 响应 VO")
public class UserLoginRespVO {

    @Schema(title = "用户token")
    private String token;

    @Schema(title = "用户ID")
    private Long id;

    @Schema(title = "用户昵称")
    private String userName;

    @Schema(title = "头像")
    private String userImg;

    @Schema(title = "性别")
    private int userSex;

    @Schema(title = "属性ID")
    private Integer userStatsId;

    @Schema(title = "登录类型")
    private Integer signType;

    @Schema(title = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signTime;

    @Schema(title = "手机账号")
    private String phoneAccount;

    @Schema(title = "最近登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recentLoginTime;

    @Schema(title = "邮箱账号")
    private String emailAccount;

    @Schema(title = "第一次注册使用的类型")
    private Integer firstSignType;

    @Schema(title = "角色ID")
    private Integer roleId;

    @Schema(title = "角色ID")
    private Integer versionId;

    @Schema(title = "渠道ID")
    private Integer channelId;

    @Schema(title = "注册载体")
    private Integer carrierId;


}
