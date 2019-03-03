package cn.bysj.yty.qyyg;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ClassName ServletInitializer
 * @Description TODO
 * @Author tangguo
 * @Date 2019/3/3 19:51
 * @Version 1.0
 * @Type class
 **/
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(QyygApplication.class);
    }

}
