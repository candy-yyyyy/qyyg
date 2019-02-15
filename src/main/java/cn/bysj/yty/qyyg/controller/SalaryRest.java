package cn.bysj.yty.qyyg.controller;

import cn.bysj.yty.qyyg.common.ControllerMapping;
import cn.bysj.yty.qyyg.common.UrlMapping;
import cn.bysj.yty.qyyg.service.SalaryService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= ControllerMapping.SALARY)
public class SalaryRest {
    private final Logger logger = LoggerFactory.getLogger(NoticeRest.class);
    @Autowired
    private SalaryService salaryService;

    @RequestMapping(value = UrlMapping.GET_SALARY_LIST, method = RequestMethod.POST)
    public JSONObject getSalaryListByOperNo(String operNo, String startMonth, String endMonth,int pageNo, int pageSize) {
        JSONObject rspJson = new JSONObject();
        try{
            rspJson = salaryService.getSalaryInfoListByOperNo(operNo, startMonth, endMonth,pageNo,pageSize);
            rspJson.put("respCode","0000");
            rspJson.put("respDesc","查询薪资信息成功");
        }catch(Exception e){
            logger.error("查询薪资信息异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询薪资信息异常");
        }
        return rspJson;
    }
}
