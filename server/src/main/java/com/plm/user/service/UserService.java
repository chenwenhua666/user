package com.plm.user.service;

import com.plm.user.dataobject.UserInfo;

/**
 * @author : cwh
 * 2019/1/2 0002
 * description ：
 */
public interface UserService {
    UserInfo findByOpenid(String openid);
}
