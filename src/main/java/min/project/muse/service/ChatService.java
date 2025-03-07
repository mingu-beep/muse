package min.project.muse.service;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.chat.Chat;
import min.project.muse.domain.chat.ChatRepository;
import min.project.muse.domain.chatRoom.ChatRoom;
import min.project.muse.domain.chatRoom.ChatRoomRepository;
import min.project.muse.web.dto.chat.ChatMessageDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private static ChatRepository chatRepository;
    private static ChatRoomRepository chatRoomRepository;

    public Chat createChat(Long roomId, ChatMessageDTO chatMessageDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + roomId));

        return Chat.builder()
                .chatRoom(chatRoom)
                .sender(chatMessageDTO.getSender())
                .message(chatMessageDTO.getContent()).build();
    }
}
