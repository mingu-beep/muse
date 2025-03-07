package min.project.muse.domain.chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import min.project.muse.domain.chatRoom.ChatRoom;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    private String sender;

    private String message;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime sendDate;

    public static Chat createChat(ChatRoom room, String sender, String message) {
        return Chat.builder()
                .chatRoom(room)
                .sender(sender)
                .message(message)
                .build();
    }
}
