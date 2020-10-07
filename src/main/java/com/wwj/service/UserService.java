package com.wwj.service;

import com.wwj.bean.User;
import com.wwj.bean.UserExample;
import com.wwj.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 判断数据表中是否有当前用户
     * @param name
     * @return
     */
    public List<User> ifExists(String name){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(name);
        return userMapper.selectByExample(userExample);
    }

    /**
     * 锁定指定用户，锁定后的用户将无法登录系统
     * @param user
     * @return
     */
    public int lockUser(User user){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(user.getId());
        user.setStatus("0");
        return userMapper.updateByExampleSelective(user,userExample);
    }

    /**
     * 激活指定用户，激活后的用户可以正常登录系统
     * @param user
     * @return
     */
    public int activeUser(User user){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(user.getId());
        user.setStatus("1");
        return userMapper.updateByExampleSelective(user,userExample);
    }
}
