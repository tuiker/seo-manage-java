package com.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.business.common.response.ResultVO;
import com.business.common.util.EasyExcelUtil;
import com.business.common.util.SecurityUtils;
import com.business.common.vo.PageResult;
import com.business.controller.pc.dto.BlogGenerationRecordPageReqDTO;
import com.business.controller.pc.vo.BlogGenerationRecordVO;
import com.business.model.dao.BlogGenerationRecordMapper;
import com.business.model.pojo.BlogGenerationRecord;
import com.business.service.IBlogGenerationRecordService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 博客生成记录 Service接口实现
 * @Author yxf
 */
@Service
public class BlogGenerationRecordServiceImpl extends ServiceImpl<BlogGenerationRecordMapper, BlogGenerationRecord> implements IBlogGenerationRecordService {

    /**
     * 分页查询博客生成记录列表
     * @param reqDTO
     * @return
     */
    @Override
    public PageResult<BlogGenerationRecordVO> pageList(BlogGenerationRecordPageReqDTO reqDTO) {
        Page<BlogGenerationRecordVO> page = baseMapper.pageList(new Page<>(reqDTO.getPage(), reqDTO.getPageSize()), reqDTO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }
}
