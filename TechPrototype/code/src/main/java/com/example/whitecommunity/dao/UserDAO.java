package com.example.whitecommunity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.whitecommunity.pojo.User;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findById(int id);

    User findByUsername(String username);

    List<User> findByUsernameLike(String username);

    long countByUsername(String username);

    @Query(value = "SELECT users.* FROM users, user_followings " +
            "WHERE user_followings.followee = :followee AND " +
            "users.id = user_followings.follower AND " +
            "user_followings.valid = 1 " +
            "ORDER BY user_followings.update_time DESC " +
            "limit :pageNum, :pageSize", nativeQuery = true)
    List<User> getFollowers(int followee, int pageSize, int pageNum);

    @Query(value = "SELECT users.* FROM users, user_followings " +
            "WHERE user_followings.follower = :follower AND " +
            "users.id = user_followings.followee AND " +
            "user_followings.valid = 1 " +
            "ORDER BY user_followings.update_time DESC " +
            "LIMIT :pageNum, :pageSize", nativeQuery = true)
    List<User> getFollowees(int follower, int pageSize, int pageNum);

    /**
     * 查询所有关注的用户 (不分页)
     *
     * @param follower 关注着 id
     * @return 关注的用户的 id 的列表
     */
    @Query(value = "SELECT followee FROM user_followings " +
            "WHERE follower = :follower AND " +
            "valid = 1 " +
            "ORDER BY update_time DESC ", nativeQuery = true)
    List<Integer> getFolloweesNoPage(int follower);

}
