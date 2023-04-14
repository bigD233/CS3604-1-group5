package com.example.whitecommunity.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.whitecommunity.pojo.UserMessage;

public interface UserMessageDAO extends JpaRepository<UserMessage, Integer> {
    @Query(value = "SELECT * FROM user_messages m WHERE" +
            "(message_from = :id1 AND message_to = :id2) OR " +
            "(message_from = :id2 AND message_to = :id1) " +
            "ORDER BY create_time DESC", nativeQuery = true)
    Page<UserMessage> getMessageByIds(int id1, int id2, Pageable pageable);
}
