package cn.bysj.yty.qyyg.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionUtil {

    private static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static void setSession(String key, String value) {
        HttpServletRequest request = getHttpServletRequest();
        request.getSession().setAttribute(key, value);
    }

    public static String getSessionStr(String key) {
        HttpServletRequest request = getHttpServletRequest();
        return request.getSession().getAttribute(key) == null ? "" : request.getSession().getAttribute(key).toString();
    }

}
