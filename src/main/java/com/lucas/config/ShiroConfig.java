package com.lucas.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig<ShiroDialet> {
    //shiroFilterFactoryBean 3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean( );
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //增加shiro的内置过滤器
        /**
         * anon:无需认证就可以访问
         * authc:必须认证了才可能访问
         * user:必须有用 记住我 功能才能使用
         * perms:拥有对某个资源的权限才能访问
         * role：拥有某个角色权限才可以访问
         */
        //拦截
        Map<String, String> filterMap=new LinkedHashMap<>();
        //filterMap.put("/user/add","authc");
        //filterMap.put("/user/update","authc");
        //授权,正常情况未授权回调到未授权界面。下列两行代码都是为了让 add 和update不授权
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");


        filterMap.put("/user/*","authc");


        bean.setFilterChainDefinitionMap(filterMap);
        //没有认证跳回登录界面
        bean.setLoginUrl("/toLogin");
        //设置未授权请求,跳转
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }

    //DefaultWebSecurityManager 2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建Realm对象，需要自定义类。1
    @Bean
    public UserRealm userRealm(){

        return new UserRealm();
    }

    //整合shiroDialet,用来整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialet(){
        ShiroDialect shiroDialect = new ShiroDialect();
        return shiroDialect;
    }

}
