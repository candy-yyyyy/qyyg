package cn.bysj.yty.qyyg.controller;

import cn.bysj.yty.qyyg.common.ControllerMapping;
import cn.bysj.yty.qyyg.common.UrlMapping;
import cn.bysj.yty.qyyg.service.LogService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= ControllerMapping.LOG)
public class LogRest {
    private final Logger logger = LoggerFactory.getLogger(LogRest.class);

    @Autowired
    private LogService logService;

    @RequestMapping(value = UrlMapping.GET_LOG, method = RequestMethod.POST)
    public JSONObject getLog(String operNo, String startTime, String endTime, Integer pageNo, Integer pageSize) {
        JSONObject rspJson = new JSONObject();
        if(pageNo==null){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数pageNo不能为空！");
            return rspJson;
        }
        if(pageSize==null){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数pageSize不能为空！");
            return rspJson;
        }
        try{
            rspJson = logService.qryLogByCondition(operNo,startTime,endTime,pageNo,pageSize);
            if(rspJson!=null){
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","查询日志列表成功！");
            }else{
                rspJson = new JSONObject();
                rspJson.put("respCode","9999");
                rspJson.put("respDesc","查询日志列表失败！");
            }
        }catch(Exception e){
            logger.error("查询日志列表异常：",e);
            rspJson = new JSONObject();
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询日志列表异常！");
        }
        return rspJson;
    }
}
