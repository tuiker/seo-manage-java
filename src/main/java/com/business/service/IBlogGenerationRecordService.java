package com.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.business.common.response.ResultVO;
import com.business.common.vo.PageResult;
import com.business.controller.pc.dto.BlogGenerationRecordPageReqDTO;
import com.business.controller.pc.vo.BlogGenerationRecordVO;
import com.business.model.pojo.BlogGenerationRecord;


/**
 * 博客生成记录 Service接口
 * @Author yxf
 **/
public interface IBlogGenerationRecordService extends IService<BlogGenerationRecord> {

    /**
     * 分页查询博客生成记录列表
     * @param reqDTO
     * @return
     */
    PageResult<BlogGenerationRecordVO> pageList(BlogGenerationRecordPageReqDTO reqDTO);

}
