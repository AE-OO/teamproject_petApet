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
            ,countQuery ="select count(m) from Message m where (m.messageReceiver=:messageReceiver and m.member.memberId=:memberId)"+
            "or (m.messageReceiver=:memberId and m.member.memberId=:messageReceiver)")
    Page<Message> getMessageList(String memberId, String messageReceiver, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Message m set m.messageCheck = true " +
            "where m.member.memberId =:messageReceiver and m.messageReceiver =:memberId and m.messageCheck = false")
    void updateMessageCheck(String memberId, String messageReceiver);

    @Query(value = "select ifnull(max(m.roomNumber),0) from Message m", nativeQuery = true)
    int maxRoomNumber();

    //메세지 이력 있는지 없는지 검사
    @Query("select count(m) from Message m where (m.messageReceiver=:messageReceiver and m.member.memberId=:memberId) or (m.messageReceiver=:memberId and m.member.memberId=:messageReceiver)")
    int existMessageList(String memberId, String messageReceiver);

    //기존 메세지 내역의 roomNumber가져옴
    @Query(value = "select m.roomNumber from Message m " +
            "where (m.messageReceiver=:messageReceiver and m.memberId=:memberId) " +
            "or (m.messageReceiver=:memberId and m.memberId=:messageReceiver) limit 0,1",nativeQuery = true)
    int getRoomNumber(String memberId, String messageReceiver);

    @Query("select m from Message m where m.messageId in(select max(m1.messageId) from Message m1 group by m1.roomNumber) and (m.member.memberId=:memberId or m.messageReceiver=:memberId)")
    Page<Message> getMessageRoomList(String memberId,Pageable pageable);

    @Query("select count(m) from Message m where m.messageReceiver=:memberId and m.messageCheck=false and m.roomNumber=:roomNumber")
    int unCheckedMessageSize(String memberId,int roomNumber);
}
