package com.business.model.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.business.model.pojo.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper extends MPJBaseMapper<SysRoleMenu> {

    List<Integer> getRoleMenuIds(Integer roleId);

}