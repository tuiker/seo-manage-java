package com.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.business.model.pojo.SysMenu;
import com.business.controller.pc.vo.SysMenuRespVO;

import java.util.List;

/**
 * 菜单或按钮权限 Service接口
 * @Author yxf
 **/
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据用户获取它的权限列表
     */
    List<SysMenuRespVO> getSysMenuList();

}
