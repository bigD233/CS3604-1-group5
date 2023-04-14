package com.example.whitecommunity.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.whitecommunity.dao.UserPunishmentDAO;
import com.example.whitecommunity.pojo.UserPunishment;

@Service
public class UserPunishmentService {
    @Autowired
    UserPunishmentDAO userPunishmentDAO;

    public UserPunishment get(int userId) {
        Date now = new Date();
        Timestamp endTime = Timestamp.from(now.toInstant());
        return userPunishmentDAO.findByUserIdAndEndTimeGreaterThanAndValid(userId, endTime, 1);
    }

    public List<UserPunishment> getAll() {
        Date now = new Date();
        Timestamp endTime = Timestamp.from(now.toInstant());
        return userPunishmentDAO.findByEndTimeGreaterThanAndValid(endTime, 1);
    }

    public void stop(int userId) {
        UserPunishment userPunishment = get(userId);
        if (userPunishment == null) {
            return;
        }
        userPunishment.setValid(0);
        userPunishmentDAO.save(userPunishment);
    }

    public void punish(int userId, int days) {
        if (days <= 0) {
            return;
        }

        Date start = new Date();
        Timestamp startTime = Timestamp.from(start.toInstant());
        Date end = new Date(start.getTime() + days * 24l * 60 * 60 * 1000);
        Timestamp endTime = Timestamp.from(end.toInstant());

        UserPunishment oldPunishment = get(userId);

        if (oldPunishment != null && oldPunishment.getEndTime().after(endTime)) {
            return;
        }

        UserPunishment newPunishment = new UserPunishment();
        newPunishment.setUserId(userId);
        newPunishment.setStartTime(startTime);
        newPunishment.setEndTime(endTime);
        newPunishment.setValid(1);
        userPunishmentDAO.save(newPunishment);

        if (oldPunishment != null) {
            oldPunishment.setValid(0);
            userPunishmentDAO.save(oldPunishment);
        }
    }
}
