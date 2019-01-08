package com.plm.user.repository;

import com.plm.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : cwh
 * 2019/1/2 0002
 * description ：
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,String> {
    UserInfo findByOpenid(String openid);
}
