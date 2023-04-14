package com.example.whitecommunity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.whitecommunity.pojo.UserFollowing;

public interface UserFollowingDAO extends JpaRepository<UserFollowing, Integer> {
    UserFollowing findByFolloweeAndFollower(int followee, int follower);

    int countByFolloweeAndValid(int followee, boolean valid);

    int countByFollowerAndValid(int follower, boolean valid);
}
