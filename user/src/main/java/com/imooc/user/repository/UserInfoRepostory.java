package com.imooc.user.repository;

import com.imooc.user.dataOjbect.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserInfoRepostory extends JpaRepository<UserInfo,String>{

    UserInfo findByOpenid(String openid);
}
