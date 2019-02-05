package cn.bysj.yty.qyyg.controller;

import cn.bysj.yty.qyyg.common.ControllerMapping;
import cn.bysj.yty.qyyg.common.UrlMapping;
import cn.bysj.yty.qyyg.service.BaseService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= ControllerMapping.BASE)
public class BaseRest {
    private final Logger logger = LoggerFactory.getLogger(BaseRest.class);
    @Autowired
    private BaseService baseService;
    @RequestMapping(value = UrlMapping.GET_STATIC_INFO, method = RequestMethod.POST)
    public JSONObject getStaticInfo(String entityName) {
        logger.info("获取静态数据参数====entityName:"+entityName);
        JSONObject rspJson = new JSONObject();
        try{
            JSONArray arr = baseService.queryInfoByEntity(entityName);
            if(arr!=null && arr.size()>0){
                rspJson.put("args",arr);
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","查询静态数据成功");
            }else{
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","查询静态数据为空");
            }
        }catch(Exception e){
            logger.error("查询静态数据异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询静态数据异常,请联系系统管理员");
        }
        return rspJson;
    }
}
