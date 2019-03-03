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
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping(value=ControllerMapping.NOTICE)
public class NoticeRest {
    private final Logger logger = LoggerFactory.getLogger(NoticeRest.class);
    @Autowired
    private NoticeService noticeService;
    @RequestMapping(value = UrlMapping.GET_NOTICE_LIST_INFO, method = RequestMethod.POST)
    public JSONObject getNoticeListInfo(int pageNo,int pageSize) {
        JSONObject rspJson = new JSONObject();
        try{
            rspJson = noticeService.getNoticeList(pageNo,pageSize);
            rspJson.put("respCode","0000");
            rspJson.put("respDesc","查询公告列表信息成功");
        }catch(Exception e){
            logger.error("查询公告列表信息异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询公告列表信息异常");
        }
        return rspJson;
    }
    @RequestMapping(value = UrlMapping.ADD_NOTICE, method = RequestMethod.POST)
    public JSONObject addNotice(String operNo,String noticeTitle,String noticeContent) {
        JSONObject rspJson = new JSONObject();
        if(StringUtils.isEmpty(operNo)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数operNo不能为空！");
            return rspJson;
        }
        if(StringUtils.isEmpty(noticeTitle)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数noticeTitle不能为空！");
            return rspJson;
        }
        if(StringUtils.isEmpty(noticeContent)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数noticeContent不能为空！");
            return rspJson;
        }

        try{
            Boolean flag = noticeService.addNotince(operNo, noticeTitle, noticeContent);
            if(flag){
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","新增公告新闻成功");
            }else{
                rspJson.put("respCode","9999");
                rspJson.put("respDesc","新增公告新闻失败");
            }
        }catch(Exception e){
            logger.error("新增公告新闻异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","新增公告新闻异常");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.GET_NOTICE, method = RequestMethod.POST)
    public JSONObject getNotice(int noticeId) {
        JSONObject rspJson = new JSONObject();
        try{
            Notice notice = noticeService.getNoticeById(noticeId);
            if(notice!=null){
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","查询公告信息成功");
                rspJson.put("notice", JSON.toJSON(notice));
            }else{
                rspJson.put("respCode","9999");
                rspJson.put("respDesc","查询公告信息失败");
            }
        }catch(Exception e){
            logger.error("查询公告信息异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询公告信息异常");
        }
        return rspJson;
    }
}
