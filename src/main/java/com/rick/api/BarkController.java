package com.rick.api;

import com.rick.notification.bark.PushNotification;
import com.rick.notification.bark.PushNotificationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public void message(@RequestParam String title, String subtitle, @RequestParam String body) {
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(body)) {
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
}
