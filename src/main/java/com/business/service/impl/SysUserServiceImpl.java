package com.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.business.auth.entity.LoginUser;
import com.business.common.constant.CommonNum;
import com.business.common.enums.ResultCode;
import com.business.common.util.SecurityUtils;
import com.business.common.util.SystemNumUtil;
import com.business.common.vo.PageResult;
import com.business.controller.pc.dto.*;
import com.business.model.dao.SysUserMapper;
import com.business.model.pojo.SysUser;
import com.business.model.redis.LoginUserRedisDAO;
import com.business.common.response.ResultVO;
import com.business.controller.pc.vo.UserInfoVO;
import com.business.controller.pc.vo.UserLoginRespVO;
import com.business.service.ISysUserService;
import com.business.common.util.MD5Utils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Author: GaoLu
 * @Date: 2023-10-18 14:08
 * @Description: 用户信息表 服务类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private LoginUserRedisDAO loginUserRedisDAO;

    /**
     * 用户登录
     * @param reqDTO
     * @return
     */
    @Transactional
    @Override
    public ResultVO<UserLoginRespVO> loginUser(UserLoginReqDTO reqDTO) {
        SysUser user = this.lambdaQuery().eq(SysUser::getUserName, reqDTO.getUsername())
                .eq(SysUser::getPassword, MD5Utils.MD5(reqDTO.getPassword())).one();
        if (null != user) {
            //登录成功，创建token，存入缓存
            String token = IdUtil.fastSimpleUUID();
            LoginUser loginUser = BeanUtil.copyProperties(user, LoginUser.class);
            loginUser.setToken(token);
            loginUserRedisDAO.set(token, loginUser);

            //修改最近登录时间
            SysUser updateUser = new SysUser();
            updateUser.setId(user.getId());
            updateUser.setRecentLoginTime(LocalDateTime.now());
            sysUserMapper.updateById(updateUser);

            //将用户的基本信息及token返回给前端
            UserLoginRespVO respVO = BeanUtil.copyProperties(user, UserLoginRespVO.class);
            respVO.setToken(token);
            return ResultVO.success(respVO);
        } else
            return ResultVO.error(ResultCode.ERROR_USER_OR_PASSWORD);
    }


    public UserInfoVO getUserInfoById(long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        return BeanUtil.copyProperties(sysUser, UserInfoVO.class);
    }

    /**
     * 分页查询用户列表
     * @param reqDTO
     * @return
     */
    @Override
    public PageResult<UserInfoVO> pageList(UserPageReqDTO reqDTO) {
        if(StrUtil.isNotBlank(reqDTO.getUserName())){//模糊查询用户账号
            reqDTO.setUserName("%" + reqDTO.getUserName() + "%");
        }
        Page<UserInfoVO> page = sysUserMapper.pageList(new Page<>(reqDTO.getPage(), reqDTO.getPageSize()), reqDTO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    /**
     * 添加系统用户
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<Boolean> addSysUser(SysUserAddReqDTO reqDTO) {
        if(checkIsExists(reqDTO.getUserName(), null)){
            return ResultVO.success("该用户账号已被占用", false);
        }

        SysUser user = BeanUtil.copyProperties(reqDTO, SysUser.class);
        //密码进行加密
        user.setPassword(MD5Utils.MD5(reqDTO.getPassword()));
        user.setId(getUserId());
        user.setCreateId(SecurityUtils.getLoginUserId());
        user.setCreateTime(LocalDateTime.now());
        user.setChannelId(CommonNum.ONE);
        this.save(user);

        return ResultVO.success(true);
    }

    /**
     * 修改系统用户
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<Boolean> updateSysUser(SysUserUpdateReqDTO reqDTO) {
        if(checkIsExists(reqDTO.getUserName(), reqDTO.getId())){
            return ResultVO.success("该用户账号已被占用", false);
        }

        SysUser user = BeanUtil.copyProperties(reqDTO, SysUser.class);
        user.setUpdateId(SecurityUtils.getLoginUserId());
        user.setUpdateTime(LocalDateTime.now());
        this.updateById(user);

        return ResultVO.success(true);
    }

    /**
     * 修改系统用户密码
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<Boolean> updateSysUserPassword(SysUserPasswordUpdateReqDTO reqDTO) {
        SysUser sysUser = new SysUser();
        sysUser.setId(reqDTO.getId());
        sysUser.setPassword(MD5Utils.MD5(reqDTO.getPassword()));
        this.updateById(sysUser);
        return ResultVO.success(true);
    }

    /**
     * 校验用户账号是否已存在
     * @param userName 用户账号
     * @return true：已存在， false：不存在
     */
    private boolean checkIsExists(String userName, Long userId){
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, userName);
        if(null != userId){
            queryWrapper.ne(SysUser::getId, userId);
        }
        long count = this.count(queryWrapper);
        return count > 0;
    }

    /**
     * @Description 生成用户ID 随机6位数+用户总数 拼接
     * @Author GaoLu
     * @Date 2023/11/6
     * @Return
     **/
    private long getUserId() {
        String num = SystemNumUtil.getRandomNumberByNum(CommonNum.SIX);
        long allUserNum = this.count();
        String allUserStr = String.valueOf(allUserNum);
        String userIdOne = num + allUserStr;
        return Long.parseLong(userIdOne);
    }

}
