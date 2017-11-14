package com.lanou.service;

import com.lanou.bean.SysUser;

import java.util.List;

/**
 * Created by dllo on 17/11/9.
 */
public interface SysUserService {
    List<SysUser> getAllUser();

    SysUser getUserByName(String username);
}
