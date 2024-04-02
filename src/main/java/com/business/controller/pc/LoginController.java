package com.business.controller.pc;

import cn.hutool.core.util.StrUtil;
import com.business.auth.entity.LoginUser;
import com.business.common.util.SecurityUtils;
import com.business.common.enums.ResultCode;
import com.business.controller.pc.dto.UserLoginReqDTO;
import com.business.model.redis.LoginUserRedisDAO;
import com.business.common.response.ResultVO;
import com.business.controller.pc.vo.UserInfoVO;
import com.business.controller.pc.vo.UserLoginRespVO;
import com.business.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: LoginController
 * @Description: 登录
 * @Author: Sam
 * @Date: 2023-11-10 13:14
 * @Version: 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/pc/user")
@Tag(name = "后台用户控制控制层")
public class LoginController {

    @Resource
    ISysUserService userInfoService;

    @Resource
    private LoginUserRedisDAO loginUserRedisDAO;

//    @Operation(summary = "用户登录")
//    @PostMapping("/login")
//    public ResultVO loginUser(@RequestBody UserDto dto) {
//        return userInfoService.loginUser(dto);
//    }

    @Operation(summary = "用户登录")
    @PostMapping("/userLogin")
    public ResultVO<UserLoginRespVO> userLogin(@RequestBody UserLoginReqDTO reqDTO) {
        if(StrUtil.isBlank(reqDTO.getUsername()) || StrUtil.isBlank(reqDTO.getPassword())){
            return ResultVO.error(ResultCode.VALIDATE_FAILED);
        }

        return userInfoService.loginUser(reqDTO);
    }

    @Operation(summary = "用户登出")
    @PostMapping("/loginOut")
    public ResultVO<Boolean> loginOut(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(null != loginUser){
            loginUserRedisDAO.remove(loginUser.getToken());
        }
        return ResultVO.success(true);
    }

    @Operation(summary = "获取我的个人信息")
    @GetMapping("/getMyUserInfo")
    public ResultVO<UserInfoVO> getMyUserInfo() {
        Long loginUserId = SecurityUtils.getLoginUserId();
        return ResultVO.success(userInfoService.getUserInfoById(loginUserId));
    }

}
