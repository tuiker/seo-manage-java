package com.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.business.common.response.ResultVO;
import com.business.common.util.SecurityUtils;
import com.business.common.vo.PageResult;
import com.business.controller.pc.dto.TemplateAddReqDTO;
import com.business.controller.pc.dto.TemplatePageReqDTO;
import com.business.controller.pc.dto.TemplateUpdateReqDTO;
import com.business.controller.pc.vo.TemplateInfoVO;
import com.business.model.dao.TemplateInfoMapper;
import com.business.model.pojo.TemplateInfo;
import com.business.service.ITemplateInfoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * 模板信息 Service接口实现
 * @Author yxf
 */
@Service
public class TemplateInfoServiceImpl extends ServiceImpl<TemplateInfoMapper, TemplateInfo> implements ITemplateInfoService {


    /**
     * 分页查询模板信息列表
     * @param reqDTO
     * @return
     */
    @Override
    public PageResult<TemplateInfoVO> pageList(TemplatePageReqDTO reqDTO) {
        if(StrUtil.isNotBlank(reqDTO.getTemplateTitle())){//模糊模板标题
            reqDTO.setTemplateTitle("%" + reqDTO.getTemplateTitle() + "%");
        }
        Page<TemplateInfoVO> page = baseMapper.pageList(new Page<>(reqDTO.getPage(), reqDTO.getPageSize()), reqDTO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    /**
     * 新增模板信息
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<Boolean> add(TemplateAddReqDTO reqDTO) {
        if(checkTitleIsExists(reqDTO.getTemplateTitle(), null)){
            return ResultVO.error("模板标题已存在");
        }
        TemplateInfo templateInfo = BeanUtil.copyProperties(reqDTO, TemplateInfo.class);
        templateInfo.setCreateId(SecurityUtils.getLoginUserId());
        templateInfo.setCreateTime(LocalDateTime.now());
        this.save(templateInfo);
        return ResultVO.success(true);
    }

    /**
     * 修改模板信息
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<Boolean> update(TemplateUpdateReqDTO reqDTO) {
        if(checkTitleIsExists(reqDTO.getTemplateTitle(), reqDTO.getId())){
            return ResultVO.error("模板标题已存在");
        }
        TemplateInfo templateInfo = BeanUtil.copyProperties(reqDTO, TemplateInfo.class);
        templateInfo.setUpdateId(SecurityUtils.getLoginUserId());
        templateInfo.setUpdateTime(LocalDateTime.now());
        this.updateById(templateInfo);
        return ResultVO.success(true);
    }

    /**
     * 校验模板标题是否已存在
     * @param title 模板标题
     * @param id 模板ID
     * @return true：已存在，false：不存在
     */
    private boolean checkTitleIsExists(String title, Long id){
        LambdaQueryWrapper<TemplateInfo> queryWrapper = new LambdaQueryWrapper<TemplateInfo>().eq(TemplateInfo::getTemplateTitle, title);
        if(id != null){
            queryWrapper.ne(TemplateInfo::getId, id);
        }
        long count = this.count(queryWrapper);
        return count > 0;
    }
}
