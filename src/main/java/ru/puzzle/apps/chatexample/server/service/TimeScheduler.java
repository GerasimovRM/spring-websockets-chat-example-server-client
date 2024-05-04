package ru.puzzle.apps.chatexample.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.puzzle.apps.chatexample.server.model.Greeting;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class TimeScheduler {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final SimpMessagingTemplate broker;

    @Scheduled(fixedRate = 15000)
    public void runTimeScheduler() {
        String time = LocalTime.now().format(TIME_FORMAT);
        log.info("Time broadcast: {}", time);
        broker.convertAndSend("/topic/greetings", new Greeting("Current time is " + time));
    }
}
