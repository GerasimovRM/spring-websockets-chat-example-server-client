package ru.puzzle.apps.chatexample.client.config;

import org.springframework.lang.NonNull;
import ru.puzzle.apps.chatexample.server.model.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
public class SessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, @NonNull StompHeaders connectedHeaders) {
        session.subscribe("/topic/greetings", this);

        log.info("New session: {}", session.getSessionId());
    }

    @Override
    public @NonNull Type getPayloadType(@NonNull StompHeaders headers) {
        return Greeting.class;
    }

    @Override
    public void handleFrame(@NonNull StompHeaders headers, Object payload) {
        log.info("Received: {}", ((Greeting) payload).getContent());
    }
}
