package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.chat.Chat;
import min.project.muse.service.ChatService;
import min.project.muse.web.dto.chat.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebSocket 데이터 처리를 수행한다.
 */
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template; // 특정 사용자에게 메시지를 보내는 데 사용되는 STOMP을 이용한 탬플릿이다.

    private final ChatService chatService;

    /**
     *
     * Message 엔드포인트로 데이터와 함께 호출을 하면 "/sub/message"를 수신하는 사용자에게 메시지를 전달한다.
     *
     * @param chatMessageDTO
     * @return
     */
    @MessageMapping("/message")
    public ChatMessageDTO send(@RequestBody ChatMessageDTO chatMessageDTO) {
        template.convertAndSend("/sub/message", chatMessageDTO.getContent()); // 구독 중인 모든 사용자에게 메시지를 전달한다.

        return chatMessageDTO;
    }

    @MessageMapping("/{roomId}")
    @SendTo("/room/{roomId}")
    public Chat chat(@RequestParam(name = "roomId") Long roomId, @RequestBody ChatMessageDTO chatMessageDTO) {
        return chatService.createChat(roomId, chatMessageDTO);
    }
}
