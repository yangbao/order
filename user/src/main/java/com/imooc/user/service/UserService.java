package com.imooc.user.service;

import com.imooc.user.dataOjbect.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


public interface UserService {

    UserInfo findByOpenId(String openId) ;

}
