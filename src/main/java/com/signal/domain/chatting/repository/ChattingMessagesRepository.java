package com.signal.domain.chatting.repository;

import com.signal.domain.chatting.model.ChattingMessages;

import io.lettuce.core.dynamic.annotation.Param;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChattingMessagesRepository extends JpaRepository<ChattingMessages, Long> {
    List<ChattingMessages> findByChattingRoomIdAndIsReadFalse(Long roomId);
    
    @Query("SELECT m FROM ChattingMessages m WHERE m.chattingRoom.id = :roomId AND m.id > :cursor ORDER BY m.id ASC")
    List<ChattingMessages> findMessagesByRoomIdWithCursor(@Param("roomId") Long roomId, @Param("cursor") Long cursor, Pageable pageable);
    
    
    @Query("SELECT COUNT(cm) FROM ChattingMessages cm WHERE cm.chattingRoom.id = :roomId AND cm.isRead = false")
    Long countUnreadMessagesByRoomId(@Param("roomId") Long roomId);
    
    @Query("SELECT COUNT(cm) FROM ChattingMessages cm WHERE cm.isRead = false")
    Long countUnreadMessagesAcrossAllRooms();

    @Modifying
    @Query("UPDATE ChattingMessages cm SET cm.isRead = true WHERE cm.chattingRoom.id = :roomId AND cm.isRead = false")
    void markMessagesAsReadByRoomId(@Param("roomId") Long roomId);


}
