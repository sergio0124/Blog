package reznikov.sergey.blog.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    @Data
    private class IdDateTime {
        Long id;
        Instant dateTime = Instant.now();
    }

    private static int TIME_OF_CODE_LIFE = 10;

    private ConcurrentHashMap<String, IdDateTime> map = new ConcurrentHashMap<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SchedulerLock(name = "task_lock", lockAtLeastFor = "PT1M")
    @Scheduled(fixedDelayString = "2000", initialDelay = 1000)
    public void scheduledTask() throws InterruptedException {
        map.forEach((k, v) -> {
            if (v.getDateTime().until(Instant.now(), ChronoUnit.MINUTES) > TIME_OF_CODE_LIFE) {
                map.remove(k);
            }
        });
    }


    public String putId(Long id) {
        String code = UUID.randomUUID().toString();
        IdDateTime idDateTime = new IdDateTime();
        idDateTime.setId(id);
        map.put(code, idDateTime);
        return code;
    }

    public Long getIdByCode(String code) {
        return map.containsKey(code)
                ? map.get(code).getId()
                : null;
    }

    public void deleteCode(String code) {
        map.remove(code);
    }

    public boolean isCodeExists(String code) {
        return map.containsKey(code);
    }
}
