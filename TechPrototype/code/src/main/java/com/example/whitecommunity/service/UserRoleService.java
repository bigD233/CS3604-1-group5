package com.example.whitecommunity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.whitecommunity.dao.UserRoleDAO;
import com.example.whitecommunity.pojo.UserRole;

@Service
public class UserRoleService {
    @Autowired
    UserRoleDAO userRoleDAO;

    private String getRoleName(int roleId) {
        if (roleId == 1) {
            return "admin";
        }
        return null;
    }

    public List<String> getRoles(int userId) {
        List<UserRole> userRoles = userRoleDAO.findByUserId(userId);
        List<String> roles = new ArrayList<>();

        for (UserRole userRole : userRoles) {
            String roleName = getRoleName(userRole.getRoleId());

            if (roleName != null) {
                roles.add(roleName);
            }
        }

        return roles;
    }
}
