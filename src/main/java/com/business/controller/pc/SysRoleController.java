package com.business.controller.pc;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.business.common.response.ResultVO;
import com.business.common.vo.PageResult;
import com.business.controller.pc.dto.SysRoleAddReqDTO;
import com.business.controller.pc.dto.SysRoleUpdateReqDTO;
import com.business.controller.pc.vo.SysRoleVO;
import com.business.model.base.PageDaoEntity;
import com.business.model.pojo.SysRole;
import com.business.service.ISysRoleMenuService;
import com.business.service.ISysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pc/sysRole")
@Tag(name = "admin端 - 后台角色控制层")
public class SysRoleController {

    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private ISysRoleMenuService sysRoleMenuService;

    @Operation(summary = "分页查询角色列表")
    @GetMapping("/pageList")
    public ResultVO<PageResult<SysRoleVO>> pageList(@ParameterObject PageDaoEntity reqDTO){
        Page<SysRole> page = sysRoleService.page(new Page<>(reqDTO.getPage(), reqDTO.getPageSize()));
        return ResultVO.success(new PageResult<>(BeanUtil.copyToList(page.getRecords(), SysRoleVO.class), page.getTotal()));
    }

    @Operation(summary = "添加角色")
    @PostMapping("/addSysRole")
    public ResultVO<Boolean> addSysRole(@RequestBody SysRoleAddReqDTO reqDTO){
        return sysRoleService.addSysRole(reqDTO);
    }

    @Operation(summary = "修改角色")
    @PostMapping("/updateSysRole")
    public ResultVO<Boolean> updateSysRole(@RequestBody SysRoleUpdateReqDTO reqDTO){
        return sysRoleService.updateSysRole(reqDTO);
    }

    @Operation(summary = "根据角色ID获取该角色绑定的权限ID数组")
    @Parameter(name = "roleId", description = "角色ID", required = true)
    @GetMapping("/getRoleMenuIds")
    public ResultVO<List<Integer>> getRoleMenuIds(@RequestParam("roleId") Integer roleId){
        return ResultVO.success(sysRoleMenuService.getRoleMenuIds(roleId));
    }

    @Operation(summary = "获取所有角色信息")
    @GetMapping("/listAllRole")
    public ResultVO<List<SysRoleVO>> listAllRole(){
        List<SysRole> list = sysRoleService.list();
        return ResultVO.success(BeanUtil.copyToList(list, SysRoleVO.class));
    }

    @Operation(summary = "根据角色ID删除角色")
    @Parameter(name = "id", description = "角色ID", required = true)
    @DeleteMapping("/deleteById")
    public ResultVO<Boolean> deleteById(@RequestParam("id") Integer id){
        return sysRoleService.deleteRoleById(id);
    }

}
