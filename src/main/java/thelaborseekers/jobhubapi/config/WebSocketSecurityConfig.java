package thelaborseekers.jobhubapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@EnableWebSocketSecurity
public class WebSocketSecurityConfig {
    @Bean
    AuthorizationManager<Message<?>> authorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages
                .anyMessage().authenticated();
        return messages.build();
    }
}
