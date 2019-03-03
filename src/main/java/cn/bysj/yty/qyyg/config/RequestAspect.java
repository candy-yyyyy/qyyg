package cn.bysj.yty.qyyg.config;

import cn.bysj.yty.qyyg.common.SessionUtil;
import cn.bysj.yty.qyyg.service.LogService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class RequestAspect {

    private Logger logger = LoggerFactory.getLogger(RequestAspect.class);

    @Autowired
    private LogService logService;

    @Before("within(cn.bysj.yty.qyyg.controller.*)")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("方法执行前...");
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        logger.info("host:" + request.getServerName());
        logger.info("url:" + request.getRequestURI());
        logger.info("ip:" + request.getRemoteHost());
        logger.info("method:" + request.getMethod());
        logger.info("class_method:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();    // 参数名
        Object[] argVals = joinPoint.getArgs(); // 参数值
        // 组装请求参数为json格式
        JSONObject args = new JSONObject();
        int i = 0;
        for (String argName : argNames
        ) {
            args.put(argName, argVals[i]);
            i++;
        }
        logger.info("args:" + args);
        SessionUtil.setSession("url", request.getRequestURI());
        SessionUtil.setSession("reqInfo", args.toJSONString());
    }

   /* @After("log()")
    public void doAfter(JoinPoint joinPoint){
        logger.info("方法执行后...");
    }*/

    @AfterReturning(returning = "result", value = "within(cn.bysj.yty.qyyg.controller.*)")
    public void doAfterReturning(Object result) {
        String operNo = SessionUtil.getSessionStr("operNo");
        String url = SessionUtil.getSessionStr("url");
        String reqInfo = SessionUtil.getSessionStr("reqInfo");
        logger.info("operNo:" + operNo);
        try {
            if (url.indexOf("getLog") == -1) {
                logService.addLog(operNo, url, reqInfo, result.toString());
            }
        } catch (Exception e) {
            logger.error("记录日志异常：", e);
        }

        logger.info("方法返回值：" + result);
    }

    public static void main(String[] args) {
        String[] arr = {"111123", "3211"};
        System.out.println(JSONArray.toJSONString(arr));

    }
}