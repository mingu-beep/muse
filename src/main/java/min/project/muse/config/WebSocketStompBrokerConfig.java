package min.project.muse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration                  // 설정 클래스로 지정한다.
@EnableWebSocketMessageBroker   // WebSocket 메시지 브로커를 활성화한다.
public class WebSocketStompBrokerConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 메시지 브로커 옵션을 구성한다.
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 구독 (sub) : 접두사로 시작하는 메시지를 브로커가 처리하도록 설정한다.
        // 클라이언트는 이 접두사로 시작하는 주제를 구독하여 메시지를 받을 수 있다.
        // ex ) 소켓 통신에서 사용자가 특정 메시지를 받기 위해 "/sub"이라는 prefix 기반 메시지 수신을 위해 Subscribe 한다.
        registry.enableSimpleBroker("/sub");

        // 발행 (pub) : 접두사로 시작하는 메시지는 @MessageMapping이 달린 메서드로 라우팅된다.
        // 클라이언트가 서버로 메시지를 보낼 때 이 접두사를 사용한다.
        // ex ) 소켓 통신에서 사용자가 특정 메시지를 전송하기 위해 "/pub"라는 prefix 기반 메시지 전송을 위해 Publish 한다.
        registry.setApplicationDestinationPrefixes("/pub");
    }

    /**
     * 각각 특정 URL에 매핑되는 STOMP 엔드 포인트를 등록하고, 선택적으로 SockJS 풀백 옵션을 활성화하고 구성한다.
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // addEndpoint : 클라이언트가 WebSocket에 연결하기 위한 엔드포인트를 "/ws-stomp"로 설정한다.
        // withSockJS : WebSocket을 지원하지 않는 브라우저에서도 SockJS를 통해 WebSocket 기능을 사용할 수 있게 한다.
        registry
                .addEndpoint("/ws-stomp")
                // 클라이언트의 origin을 명시적으로 지정
//                .setAllowedOrigins("<http://localhost:3000>")
                // WebSocket을 지원하지 않는 브라우저에서도 SockJS를 통해 WebSocket 기능을 사용할 수 있도록 한다.
                .withSockJS();
        // url : localhost:8080/ws-stomp
    }
}
