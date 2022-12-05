package reznikov.sergey.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class ServiceLogging {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * reznikov.sergey.blog.services.*.*(..))")
    public void callAtAllServicePublic() { }

    @After("callAtAllServicePublic()")
    public void afterCallAtMethod1(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        logger.info( jp.getSignature() + ", args=[" + args + "]");
    }
}
