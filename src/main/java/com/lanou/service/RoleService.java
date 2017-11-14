package com.lanou.service;

import com.lanou.bean.SysRole;

import java.util.List;

/**
 * Created by dllo on 17/11/9. */
public interface RoleService {
    List<SysRole> getAllRoles();


    int delMore(List<Integer> idz);
}
