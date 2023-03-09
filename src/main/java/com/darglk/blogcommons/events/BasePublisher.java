package com.darglk.blogcommons.events;

import com.darglk.blogcommons.utils.JSONUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@AllArgsConstructor
@Getter
@Slf4j
public abstract class BasePublisher {
    private Subjects subject;
    private KafkaTemplate<String, String> connection;

    public void publish(Object data) {
        String dataStr = JSONUtils.toJson(data);

        log.info("Publishing event {} to subject {}", dataStr, subject.getSubject());
        this.connection.send(subject.getSubject(), dataStr);
    }
}