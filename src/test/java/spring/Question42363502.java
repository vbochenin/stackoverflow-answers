package spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

public class Question42363502 {

    @Configuration
    @EnableWebSocket
    public class WebSocketConfig implements WebSocketConfigurer {



        @Override
        @MessageExceptionHandler()
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(myHandler(), "/myHandler");
        }

        @Bean
        public WebSocketHandler myHandler() {
            return new WebSocketHandler() {
                @Override
                public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

                }

                @Override
                public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

                }

                @Override
                public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

                }

                @Override
                public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

                }

                @Override
                public boolean supportsPartialMessages() {
                    return false;
                }
            };
        }

    }

}
