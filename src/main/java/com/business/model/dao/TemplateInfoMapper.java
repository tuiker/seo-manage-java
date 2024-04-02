package com.business.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.business.controller.pc.dto.TemplatePageReqDTO;
import com.business.controller.pc.vo.TemplateInfoVO;
import com.business.model.pojo.SysUser;
import com.business.model.pojo.TemplateInfo;
import org.apache.ibatis.annotations.Param;

public interface TemplateInfoMapper extends BaseMapper<TemplateInfo> {

    Page<TemplateInfoVO> pageList(Page<SysUser> page, @Param("reqDTO") TemplatePageReqDTO reqDTO);

}