package com.teamproject.petapet.domain.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select m from Message m where (m.messageReceiver=:messageReceiver and m.member.memberId=:memberId) " +
            "or (m.messageReceiver=:memberId and m.member.memberId=:messageReceiver)"
            ,countQuery ="select count(m) from Message m where (m.messageReceiver=:messageReceiver and m.member.memberId=:memberId)" )
    Page<Message> getMessageList(String memberId, String messageReceiver, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Message m set m.messageCheck = true " +
            "where m.member.memberId =:messageReceiver and m.messageReceiver =:memberId and m.messageCheck = false")
    void updateMessageCheck(String memberId, String messageReceiver);
}
