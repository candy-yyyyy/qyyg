package cn.bysj.yty.qyyg.controller;

import cn.bysj.yty.qyyg.common.ControllerMapping;
import cn.bysj.yty.qyyg.common.UrlMapping;
import cn.bysj.yty.qyyg.domain.Notice;
import cn.bysj.yty.qyyg.service.NoticeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value=ControllerMapping.NOTICE)
public class NoticeRest {
    private final Logger logger = LoggerFactory.getLogger(NoticeRest.class);
    @Autowired
    private NoticeService noticeService;
    @RequestMapping(value = UrlMapping.GET_NOTICE_LIST_INFO, method = RequestMethod.POST)
    public JSONObject getNoticeListInfo() {
        JSONObject rspJson = new JSONObject();
        try{
            List<Notice> noticeList = noticeService.getNoticeList();
            if(noticeList!=null && noticeList.size()>0){
                rspJson.put("args",JSON.toJSON(noticeList));
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","查询公告列表信息成功");
            }else{
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","查询公告列表信息为空");
            }
        }catch(Exception e){
            logger.error("查询公告列表信息异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询公告列表信息异常");
        }
        return rspJson;
    }
}
