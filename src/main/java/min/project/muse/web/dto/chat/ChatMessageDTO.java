package min.project.muse.web.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 구독자와 수신자 간의 메시지를 주고받는 형태를 구성한 Object
 */
@Data
@AllArgsConstructor
public class ChatMessageDTO {
    private String content;
    private String sender;
}
