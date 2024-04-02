package com.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.business.model.pojo.SysRoleMenu;

import java.util.List;

/**
 * 角色-权限 Service接口
 * @Author yxf
 **/
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 保存角色权限关联信息
     * @param menuIds 权限ID数组
     * @param roleId 角色ID
     * @return
     */
    Boolean saveRoleMenu(Integer[] menuIds, Integer roleId);

    /**
     * 修改角色权限关联信息
     * @param menuIds 权限ID数组
     * @param roleId 角色ID
     * @return
     */
    Boolean updateRoleMenu(Integer[] menuIds, Integer roleId);


    /**
     * 根据角色ID获取该角色绑定的权限ID数组
     * @param roleId 角色ID
     * @return
     */
    List<Integer> getRoleMenuIds(Integer roleId);

    /**
     * 根据角色ID删除角色权限关联信息
     * @param roleId
     * @return
     */
    Boolean deleteByRoleId(Integer roleId);

}
