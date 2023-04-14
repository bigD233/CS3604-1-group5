package com.example.whitecommunity.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.whitecommunity.pojo.UserMessageSummary;

public interface UserMessageSummaryDAO extends JpaRepository<UserMessageSummary, Integer> {
    UserMessageSummary findByFromAndTo(int from, int to);

    Page<UserMessageSummary> findByToOrderByUpdateTimeDesc(int to, Pageable pageable);
}
