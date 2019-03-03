package cn.bysj.yty.qyyg.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class WebInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Object operNo = httpServletRequest.getSession().getAttribute("operNo");
        String url="http://" + httpServletRequest.getServerName() //服务器地址
                + ":"
                + httpServletRequest.getServerPort()           //端口号
                + "/qyyg/login.html";
        // 存在bug--废弃
        /*if(operNo==null){
            httpServletResponse.addHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向
            httpServletResponse.addHeader("CONTEXTPATH", url);//重定向地址
            return false;
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
