package com.plm.user.service.impl;

import com.plm.user.dataobject.UserInfo;
import com.plm.user.repository.UserInfoRepository;
import com.plm.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : cwh
 * 2019/1/2 0002
 * description ：
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserInfoRepository repository;

    @Override
    public UserInfo findByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
