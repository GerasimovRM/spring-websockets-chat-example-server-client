package ru.puzzle.apps.chatexample.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import ru.puzzle.apps.chatexample.client.config.SessionHandler;
import ru.puzzle.apps.chatexample.server.model.Message;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Slf4j
public class ChatClient {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String url = "ws://127.0.0.1:8080/endpoint";
        StompSessionHandler sessionHandler = new SessionHandler();
        var session = stompClient.connectAsync(url, sessionHandler).get();
        Thread.sleep(1000);
        Scanner scanner = new Scanner(System.in);
        log.info("Enter your name:");
        String name = scanner.nextLine();
        while (true) {
            log.info("Enter message (or 'exit' to quit):");
            String message = scanner.nextLine();
            if ("exit".equalsIgnoreCase(message)) {
                break;
            }
            session.send("/app/endpoint", new Message(name, message));
        }
    }
}
