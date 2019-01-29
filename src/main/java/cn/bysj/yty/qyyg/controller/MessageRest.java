package cn.bysj.yty.qyyg.controller;

import cn.bysj.yty.qyyg.common.ControllerMapping;
import cn.bysj.yty.qyyg.common.UrlMapping;
import cn.bysj.yty.qyyg.domain.Message;
import cn.bysj.yty.qyyg.service.MessageService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping(value=ControllerMapping.MESSAGE)
public class MessageRest {
    private final Logger logger = LoggerFactory.getLogger(MessageRest.class);
    @Autowired
    private MessageService messageService;
    @RequestMapping(value = UrlMapping.GET_MESSAGE_LIST_INFO, method = RequestMethod.POST)
    public JSONObject getMessageListInfo(String pageNo,String pageSize) {
        logger.info("查询留言列表信息请求参数=====pageNo:"+pageNo+"==pageSize:"+pageSize);
        JSONObject rspJson = new JSONObject();
        if(StringUtils.isEmpty(pageNo)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数pageNo为空");
            return rspJson;
        }
        if(StringUtils.isEmpty(pageSize)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数pageSize为空");
            return rspJson;
        }
        int pageNoInt = Integer.parseInt(pageNo);
        int pageSizeInt = Integer.parseInt(pageSize);
        try{
            JSONObject messageJson = messageService.getMessageListInfo(pageNoInt,pageSizeInt);
            if(messageJson.getInteger("total")>0){
                rspJson.put("args",messageJson);
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","查询留言列表信息成功");
            }else{
                rspJson.put("args",messageJson);
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","查询留言列表信息为空");
            }
        }catch(Exception e){
            logger.error("查询留言列表信息异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询留言列表信息异常");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.ADD_MESSAGE, method = RequestMethod.POST)
    public JSONObject ADD_MESSAGE(String operNo,String content) {
        logger.info("新增留言请求参数=====operNo:"+operNo+"==content:"+content);
        JSONObject rspJson = new JSONObject();
        if(StringUtils.isEmpty(operNo)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数operNo为空");
            return rspJson;
        }
        if(StringUtils.isEmpty(content)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数content为空");
            return rspJson;
        }
        Message message = new Message();
        message.setOperNo(operNo);
        message.setContent(content);
        try{
            Boolean flag = messageService.addMessage(message);
            if(flag){
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","新增留言成功");
            }else{
                rspJson.put("respCode","9999");
                rspJson.put("respDesc","新增留言失败");
            }
        }catch(Exception e){
            logger.error("新增留言异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","新增留言异常");
        }
        return rspJson;
    }
}
