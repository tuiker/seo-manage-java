package com.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.business.common.response.ResultVO;
import com.business.controller.pc.dto.SysRoleAddReqDTO;
import com.business.controller.pc.dto.SysRoleUpdateReqDTO;
import com.business.model.pojo.SysRole;


/**
 * 角色 Service接口
 * @Author yxf
 **/
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 添加角色
     * @param reqDTO
     * @return
     */
    ResultVO<Boolean> addSysRole(SysRoleAddReqDTO reqDTO);

    /**
     * 修改角色
     * @param reqDTO
     * @return
     */
    ResultVO<Boolean> updateSysRole(SysRoleUpdateReqDTO reqDTO);

    /**
     * 根据角色ID删除角色
     * @param roleId 角色ID
     * @return
     */
    ResultVO<Boolean> deleteRoleById(Integer roleId);
}
