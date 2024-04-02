package com.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.business.common.response.ResultVO;
import com.business.common.vo.PageResult;
import com.business.controller.pc.dto.TemplateAddReqDTO;
import com.business.controller.pc.dto.TemplatePageReqDTO;
import com.business.controller.pc.dto.TemplateUpdateReqDTO;
import com.business.controller.pc.vo.TemplateInfoVO;
import com.business.model.pojo.TemplateInfo;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 模板信息 Service接口
 * @Author yxf
 **/
public interface ITemplateInfoService extends IService<TemplateInfo> {

    /**
     * 分页查询模板信息列表
     * @param reqDTO
     * @return
     */
    PageResult<TemplateInfoVO> pageList(TemplatePageReqDTO reqDTO);

    /**
     * 新增模板信息
     * @param reqDTO
     * @return
     */
    ResultVO<Boolean> add(TemplateAddReqDTO reqDTO);

    /**
     * 修改模板信息
     * @param reqDTO
     * @return
     */
    ResultVO<Boolean> update(TemplateUpdateReqDTO reqDTO);

}
