package cn.bysj.yty.qyyg.controller;

import cn.bysj.yty.qyyg.common.ControllerMapping;
import cn.bysj.yty.qyyg.common.UrlMapping;
import cn.bysj.yty.qyyg.service.JobService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping(value= ControllerMapping.JOB)
public class JobRest {
    private final Logger logger = LoggerFactory.getLogger(JobRest.class);

    @Autowired
    private JobService jobService;

    @RequestMapping(value = UrlMapping.ADD_JOB, method = RequestMethod.POST)
    public JSONObject addJob(String jobName, String jobDesc) {
        JSONObject rspJson = new JSONObject();
        if(StringUtils.isEmpty(jobName)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数jobName不能为空！");
            return rspJson;
        }
        try{
            rspJson = jobService.addJob(jobName, jobDesc);
        }catch(Exception e){
            logger.error("新增工种异常：",e);
            rspJson = new JSONObject();
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","新增工种异常");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.GET_JOB, method = RequestMethod.POST)
    public JSONObject getJob(String jobName, String state,Integer pageNo,Integer pageSize) {
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
            rspJson = jobService.qryJobByCondition(jobName, state, pageNo, pageSize);
            if(rspJson!=null){
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","查询工种列表成功！");
            }else{
                rspJson = new JSONObject();
                rspJson.put("respCode","9999");
                rspJson.put("respDesc","查询工种列表失败！");
            }
        }catch(Exception e){
            logger.error("查询工种列表异常：",e);
            rspJson = new JSONObject();
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询工种列表异常！");
        }
        return rspJson;
    }
}
