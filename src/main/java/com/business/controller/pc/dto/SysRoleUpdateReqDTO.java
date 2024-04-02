package com.business.controller.pc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "角色修改请求 DTO", description = "角色修改请求 DTO")
public class SysRoleUpdateReqDTO {

    @Schema(title = "角色ID", description = "角色ID", name = "id")
    private Integer id;

    @Schema(title = "角色名称", description = "角色名称", name = "roleName")
    private String roleName;

    @Schema(title = "角色编码", description = "角色编码", name = "roleCode")
    private String roleCode;

    @Schema(title = "角色描述", description = "角色描述", name = "roleDescribe")
    private String roleDescribe;

    @Schema(title = "权限ID数组", description = "权限ID数组", name = "menuIds")
    private Integer[] menuIds;
}
