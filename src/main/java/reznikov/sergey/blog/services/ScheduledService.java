package reznikov.sergey.blog.services;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SchedulerLock(name = "task_lock", lockAtLeastFor = "PT60M")
    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void scheduledTask() throws InterruptedException {

        logger.info("ScheduledTask работает, честно");

    }


}
