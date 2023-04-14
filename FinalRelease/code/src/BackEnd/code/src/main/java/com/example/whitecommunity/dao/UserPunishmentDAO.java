package com.example.whitecommunity.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.whitecommunity.pojo.UserPunishment;

public interface UserPunishmentDAO extends JpaRepository<UserPunishment, Integer> {
    UserPunishment findByUserIdAndEndTimeGreaterThanAndValid(int userId, Timestamp endTime, int valid);
    List<UserPunishment> findByEndTimeGreaterThanAndValid(Timestamp endTime, int valid);
}
