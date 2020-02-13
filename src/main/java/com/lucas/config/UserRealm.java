package com.lucas.config;

import com.lucas.pojo.User;
import com.lucas.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义UserRealm.
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行=》授权doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //在ShiroConfig里面未授权 add,在这里加上add权限，就可以执行add权限了


        //获取登录后的当前用户
        Subject subject= SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();

        //设置权限
        info.addStringPermission(user.getPermission());
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行=》认证doGetAuthenticationInfo");
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        User byName = userService.findByName(token.getUsername());

        if(byName==null){
            return null;
        }
        //把查找到的user保存在session中
        Subject subject1 = SecurityUtils.getSubject();
        Session session = subject1.getSession();
        session.setAttribute("loginUser",byName);
        //密码认证
        return new SimpleAuthenticationInfo(byName,byName.getPassword(),"");
    }
}
