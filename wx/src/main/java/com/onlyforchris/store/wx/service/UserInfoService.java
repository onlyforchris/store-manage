package com.onlyforchris.store.wx.service;

import com.onlyforchris.store.dal.model.User;
import com.onlyforchris.store.dal.service.UserService;
import com.onlyforchris.store.wx.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {
    @Autowired
    private UserService userService;


    public UserInfo getInfo(Integer userId) {
        User user = userService.findById(userId);
        Assert.state(user != null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }
}
