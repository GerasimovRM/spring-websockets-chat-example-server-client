package ru.puzzle.apps.chatexample.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.puzzle.apps.chatexample.server.model.Greeting;
import ru.puzzle.apps.chatexample.server.model.Message;

@Controller
@Slf4j
public class ChatController {
    @MessageMapping("/endpoint")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) {
        log.info("Received from {}: {}", message.getName(), message.getMsg());
        return new Greeting(String.format("%s: %s", message.getName(), message.getMsg()));
    }
}
