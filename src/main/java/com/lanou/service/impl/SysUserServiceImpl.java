package com.lanou.service.impl;

import com.lanou.bean.SysUser;
import com.lanou.mapper.SysUserMapper;
import com.lanou.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dllo on 17/11/9.
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getAllUser() {
        System.out.println("1" + sysUserMapper.getAllUser());
        return sysUserMapper.getAllUser();
    }

    @Override
    public SysUser getUserByName(String username) {
        System.out.println(1);
        SysUser userByName = sysUserMapper.getUserByName(username);
        System.out.println(userByName);
//        if (userByName!=null && userByName.size()>0) {
////            SysUser sysUser = new SysUser();
////            sysUser.setUsername("wangcai");
////            sysUser.setPassword("1234");
////            return sysUser;
//            return userByName.get(0);
//        }
//        else {
//            return null;
//        }
        return userByName;
    }
}
