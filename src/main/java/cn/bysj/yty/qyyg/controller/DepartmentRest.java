package cn.bysj.yty.qyyg.controller;

import cn.bysj.yty.qyyg.common.ControllerMapping;
import cn.bysj.yty.qyyg.common.UrlMapping;
import cn.bysj.yty.qyyg.service.DepartmentService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping(value= ControllerMapping.DEPARTMENT)
public class DepartmentRest {
    private final Logger logger = LoggerFactory.getLogger(DepartmentRest.class);
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = UrlMapping.ADD_DEPARTMENT, method = RequestMethod.POST)
    public JSONObject addDepartment(String departmentName, String departmentDesc,String departmentManager) {
        JSONObject rspJson = new JSONObject();
        if(StringUtils.isEmpty(departmentName)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数departmentName不能为空！");
            return rspJson;
        }
        try{
            rspJson = departmentService.addDepartment(departmentName, departmentDesc,departmentManager);
        }catch(Exception e){
            logger.error("新增部门异常：",e);
            rspJson = new JSONObject();
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","新增部门异常");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.GET_DEPARTMENT, method = RequestMethod.POST)
    public JSONObject getDepartment(String departmentName, String state,Integer pageNo,Integer pageSize) {
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
            rspJson = departmentService.queryDepartByCondition(departmentName, state, pageNo, pageSize);
            rspJson.put("respCode","0000");
            rspJson.put("respDesc","查询部门列表成功");
        }catch(Exception e){
            logger.error("查询部门列表异常：",e);
            rspJson = new JSONObject();
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询部门列表异常");
        }
        return rspJson;
    }
}
