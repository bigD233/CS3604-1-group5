package com.example.whitecommunity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.whitecommunity.pojo.UserRole;

public interface UserRoleDAO extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUserId(int userId);
}

