package com.lanou.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dllo on 2017/11/7.
 */
public class MyRealm extends AuthorizingRealm{
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return super.supports(token);
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从token中获取用户的身份信息
        String username = (String) authenticationToken.getPrincipal();

        //正常需要拿username去数据库中匹配查询
        if (!("wangcai".equals(username))){
            throw new UnknownAccountException("用户名不对");
        }

        //获取token的密码
        String password = new String((char[]) authenticationToken.getCredentials());

        //正常需要使用上面的username和password去数据库做匹配
        if (!("1234".equals(password))){
            throw new IncorrectCredentialsException("密码错了");
        }

        return new SimpleAuthenticationInfo(username,password,getName());
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // realm授权

        //在这个方法中,把用户所有的角色和权限都查出来,赋值给info
        //最终给到subject

        //拿到username
        String username = (String) principalCollection.getPrimaryPrincipal();

        //根据username去取权限/角色

        //**********************以下是模拟去数据库取值****************************

        List<String> permissions = new ArrayList<>();
        permissions.add("user:create");
        permissions.add("user:delete");

        //******************************模拟结果*********************************

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (String permission:permissions) {
            info.addStringPermission(permission);
        }
        return info;
    }
}
