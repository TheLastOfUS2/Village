package com.lanou.service.impl;

import com.lanou.bean.SysRole;
import com.lanou.mapper.SysRoleMapper;
import com.lanou.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dllo on 17/11/9.
 */
@Service
public class RoleServiceImpl implements RoleService {


    @Resource
    private SysRoleMapper sysRoleMapper;


    @Override
    public List<SysRole> getAllRoles() {
        return sysRoleMapper.getAllRoles();
    }

    @Override
    public int delMore(List<Integer> idz) {
        return sysRoleMapper.deleteByIds(idz);
    }


}
