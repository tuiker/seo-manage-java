package com.business.controller.pc;

import com.business.common.response.ResultVO;
import com.business.common.vo.PageResult;
import com.business.controller.pc.dto.ReplaceAndGenerateReqDTO;
import com.business.controller.pc.dto.TemplateAddReqDTO;
import com.business.controller.pc.dto.TemplatePageReqDTO;
import com.business.controller.pc.dto.TemplateUpdateReqDTO;
import com.business.controller.pc.vo.ReplaceResultVO;
import com.business.controller.pc.vo.TemplateInfoVO;
import com.business.service.ITemplateInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/pc/template")
@Tag(name = "admin端 - 模板信息控制层")
public class TemplateInfoController {

    @Resource
    private ITemplateInfoService templateInfoService;


    @Operation(summary = "分页查询模板信息列表")
    @GetMapping("/pageList")
    public ResultVO<PageResult<TemplateInfoVO>> pageList(@ParameterObject TemplatePageReqDTO reqDTO){
        return ResultVO.success(templateInfoService.pageList(reqDTO));
    }

    @Operation(summary = "新增模板信息")
    @PostMapping("/add")
    public ResultVO<Boolean> add(@RequestBody TemplateAddReqDTO reqDTO){
        return templateInfoService.add(reqDTO);
    }


    @Operation(summary = "修改模板信息")
    @PostMapping("/update")
    public ResultVO<Boolean> update(@RequestBody TemplateUpdateReqDTO reqDTO){
        return templateInfoService.update(reqDTO);
    }

    @Operation(summary = "根据ID删除模板信息")
    @DeleteMapping("/deleteById")
    @Parameter(name = "id", description = "模板信息ID")
    public ResultVO<Boolean> deleteById(@RequestParam("id") Long id){
        templateInfoService.removeById(id);
        return ResultVO.success(true);
    }

    @Operation(summary = "替换并生成Excel")
    @PostMapping("/replaceAndGenerate")
    public ResultVO<ReplaceResultVO> replaceAndGenerate(@RequestBody ReplaceAndGenerateReqDTO reqDTO){
        return templateInfoService.replaceAndGenerate(reqDTO);
    }

}
