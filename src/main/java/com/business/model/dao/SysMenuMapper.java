package com.business.model.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.business.model.pojo.SysMenu;
import com.business.controller.pc.vo.SysMenuRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends MPJBaseMapper<SysMenu> {

    List<SysMenuRespVO> getSysMenuListByUserId(@Param("userId") Long userId);

}