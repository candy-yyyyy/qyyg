package cn.bysj.yty.qyyg.config;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
public class RequestAspect {

    private Logger logger = LoggerFactory.getLogger(RequestAspect.class);


    @Before("within(cn.bysj.yty.qyyg.controller.*)")
    public void doBefore(JoinPoint joinPoint){
        logger.info("方法执行前...");
        ServletRequestAttributes sra=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=sra.getRequest();
        logger.info("url:"+request.getRequestURI());
        logger.info("ip:"+request.getRemoteHost());
        logger.info("method:"+request.getMethod());
        logger.info("class_method:"+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        logger.info("args:"+ Arrays.toString(joinPoint.getArgs()));
    }

   /* @After("log()")
    public void doAfter(JoinPoint joinPoint){
        logger.info("方法执行后...");
    }*/

    @AfterReturning(returning="result",value="within(cn.bysj.yty.qyyg.controller.*)")
    public void doAfterReturning(Object result){
        logger.info("方法返回值："+result);
    }
}