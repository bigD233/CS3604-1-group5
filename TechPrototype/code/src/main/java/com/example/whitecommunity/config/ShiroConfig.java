package com.example.whitecommunity.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.whitecommunity.filter.CrossOriginAnonymousFilter;
import com.example.whitecommunity.filter.JwtFilter;
import com.example.whitecommunity.realm.JwtRealm;
import com.example.whitecommunity.shiro.JwtDefaultSubjectFactory;

@Configuration
public class ShiroConfig {
    @Bean
    public SubjectFactory subjectFactory() {
        return new JwtDefaultSubjectFactory();
    }

    @Bean
    public Realm realm() {
        return new JwtRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());

        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(evaluator);

        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        factoryBean.setSecurityManager(securityManager());

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("anon", new CrossOriginAnonymousFilter());
        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);

        Map<String, String> filterRuleMap = new HashMap<>();
        filterRuleMap.put("/api/user/login", "anon");
        filterRuleMap.put("/api/user/add", "anon");
        filterRuleMap.put("/api/image/*", "anon");
        filterRuleMap.put("/api/media/*", "anon");
        filterRuleMap.put("/**", "jwt");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return factoryBean;
    }
}
