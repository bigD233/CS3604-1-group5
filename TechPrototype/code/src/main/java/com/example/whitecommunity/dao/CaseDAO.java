package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CaseDAO extends JpaRepository<CaseInfo, Integer> {
    CaseInfo findByName(String name);

    CaseInfo getByName(String name);

    List<CaseInfo> findByNameLike(String name);

    CaseInfo findById(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE caserepository SET name=:name,notes=:notes,open=:open,user_id=:userId WHERE case_id = :id", nativeQuery = true)
    void updateInfo(String name, String notes, int open, int userId, int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE caserepository SET intro=:intro,history=:history, diagnosis=:diagnosis, treatment=:treatment, prevention=:prevention ,text_type=:textType,release_Time=:releaseTime WHERE case_id = :id", nativeQuery = true)
    void updateContent(String intro, String history, String diagnosis, String treatment, String prevention, int textType, String releaseTime, int id);

    List<CaseInfo> findByUserId(int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE caserepository SET likes = :likes WHERE case_id = :id", nativeQuery = true)
    void updateLikes(int likes, int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE caserepository SET watchers = :watchers WHERE case_id = :id", nativeQuery = true)
    void updateWatchers(int watchers, int id);


    @Query(value = "SELECT caserepository.case_id FROM caserepository " +
            "ORDER BY caserepository.release_time,caserepository.case_id DESC " +
            "LIMIT :pageNum, :pageSize", nativeQuery = true)
    List<Integer> getCaseByTimeOrder(int pageSize, int pageNum);
}
