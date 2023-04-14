package com.example.whitecommunity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.whitecommunity.dao.UserDAO;
import com.example.whitecommunity.dao.UserFollowingDAO;
import com.example.whitecommunity.pojo.UserFollowing;

@Service
public class UserFollowingService {
    @Autowired
    UserFollowingDAO userFollowingDAO;

    @Autowired
    UserDAO userDAO;

    public boolean exists(int followee, int follower) {
        UserFollowing userFollowing = userFollowingDAO.findByFolloweeAndFollower(followee, follower);
        return userFollowing != null && userFollowing.getValid();
    }

    public void add(int followee, int follower) {
        UserFollowing userFollowing = userFollowingDAO.findByFolloweeAndFollower(followee, follower);
        if (userFollowing == null) {
            userFollowing = new UserFollowing();
            userFollowing.setFollowee(followee);
            userFollowing.setFollower(follower);
        }
        userFollowing.setValid(true);
        userFollowingDAO.save(userFollowing);
    }

    public void remove(int followee, int follower) {
        UserFollowing userFollowing = userFollowingDAO.findByFolloweeAndFollower(followee, follower);
        if (userFollowing == null) {
            return;
        }
        userFollowing.setValid(false);
        userFollowingDAO.save(userFollowing);
    }

    public int countFollowers(int followee) {
        return userFollowingDAO.countByFolloweeAndValid(followee, true);
    }

    public int countFollowees(int follower) {
        return userFollowingDAO.countByFollowerAndValid(follower, true);
    }
}
