package com.rick.api;

import com.rick.notification.bark.PushNotification;
import com.rick.notification.bark.PushNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rick.Xu
 * @date 2026/1/15 17:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("messages")
@CrossOrigin
public class BarkController {

    private final PushNotificationService pushNotificationService;

    @GetMapping
    public void message(String title, String subtitle, String body) {
        pushNotificationService.push(PushNotification.builder()
                .title(title)
                .subtitle(subtitle)
                .body(body)
                .badge(1)
                .sound("alarm")
                .icon("https://day.app/assets/images/avatar.jpg")
                .group("default")
                .build());
    }
}
