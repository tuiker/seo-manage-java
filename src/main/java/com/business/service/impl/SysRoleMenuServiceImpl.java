package com.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.business.model.dao.SysRoleMenuMapper;
import com.business.model.pojo.SysRoleMenu;
import com.business.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色 Service接口实现
 * @Author yxf
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    /**
     * 保存角色权限关联信息
     * @param menuIds 权限ID数组
     * @param roleId 角色ID
     * @return
     */
    @Transactional
    @Override
    public Boolean saveRoleMenu(Integer[] menuIds, Integer roleId) {
        if(null == menuIds || menuIds.length < 1){
            return true;
        }
        List<SysRoleMenu> roleMenuList = new ArrayList<>();
        for (Integer menuId : menuIds){
            roleMenuList.add(new SysRoleMenu(null, roleId, menuId));
        }
        this.saveBatch(roleMenuList);
        return true;
    }

    /**
     * 修改角色权限关联信息
     * @param menuIds 权限ID数组
     * @param roleId 角色ID
     * @return
     */
    @Transactional
    @Override
    public Boolean updateRoleMenu(Integer[] menuIds, Integer roleId) {
        //先删除
        this.deleteByRoleId(roleId);
        //再添加
        this.saveRoleMenu(menuIds, roleId);
        return true;
    }

    /**
     * 根据角色ID获取该角色绑定的权限ID数组
     * @param roleId 角色ID
     * @return
     */
    @Override
    public List<Integer> getRoleMenuIds(Integer roleId) {
        return baseMapper.getRoleMenuIds(roleId);
    }

    /**
     * 根据角色ID删除角色权限关联信息
     * @param roleId
     * @return
     */
    @Override
    public Boolean deleteByRoleId(Integer roleId) {
        this.lambdaUpdate().eq(SysRoleMenu::getRoleId, roleId).remove();
        return true;
    }
}
