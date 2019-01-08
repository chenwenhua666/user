package com.plm.user.controller;

import com.plm.user.constant.CookieConstant;
import com.plm.user.constant.RedisConstant;
import com.plm.user.dataobject.UserInfo;
import com.plm.user.enums.ResultEnum;
import com.plm.user.enums.RoleEnum;
import com.plm.user.service.UserService;
import com.plm.user.utils.CookieUtil;
import com.plm.user.utils.ResultVOUtil;
import com.plm.user.vo.ResultVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : cwh
 * 2019/1/2 0002
 * description ：
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid,
                          HttpServletResponse response){
        UserInfo userInfo = userService.findByOpenid(openid);

        if (userInfo == null){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        if (!RoleEnum.BUYER.getCode().equals(userInfo.getRole())){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        CookieUtil.set(response,CookieConstant.OPENID,openid,CookieConstant.expire);
        return ResultVOUtil.success();
    }

    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid,
                           HttpServletResponse response,
                           HttpServletRequest request){
        //判断是否登录
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie != null &&
                !StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(
                String.format(RedisConstant.TOKEN_PRE,cookie.getValue())))) {
            return ResultVOUtil.success();
        }

        UserInfo userInfo = userService.findByOpenid(openid);

        if (userInfo == null){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        if (!RoleEnum.SELLER.getCode().equals(userInfo.getRole())){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PRE,token),
                openid,CookieConstant.expire,TimeUnit.SECONDS);

        CookieUtil.set(response,CookieConstant.TOKEN,token,CookieConstant.expire);
        return ResultVOUtil.success();
    }


}
