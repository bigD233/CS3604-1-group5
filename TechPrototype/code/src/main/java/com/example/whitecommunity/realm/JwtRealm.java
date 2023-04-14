package com.example.whitecommunity.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.whitecommunity.pojo.User;
import com.example.whitecommunity.service.UserRoleService;
import com.example.whitecommunity.service.UserPunishmentService;
import com.example.whitecommunity.service.UserService;
import com.example.whitecommunity.shiro.JwtToken;
import com.example.whitecommunity.utils.JwtUtils;

public class JwtRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    UserPunishmentService userPunishmentService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String jwt = (String) principals.fromRealm("JwtRealm").iterator().next();

        int id = JwtUtils.getId(jwt);
        List<String> roles = userRoleService.getRoles(id);
        info.addRoles(userRoleService.getRoles(id));

        if (!roles.contains("normal")) {
            if (userPunishmentService.get(id) == null) {
                info.addRole("normal");
            }
        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwt = (String) token.getPrincipal();
        if (jwt == null) {
            throw new UnknownAccountException();
        }
        int id = JwtUtils.getId(jwt);
        User user = userService.get(id);
        if (user == null || !JwtUtils.verify(jwt, id, user.getPassword())) {
            throw new UnknownAccountException();
        }

        return new SimpleAuthenticationInfo(jwt, jwt, "JwtRealm");
    }
}
