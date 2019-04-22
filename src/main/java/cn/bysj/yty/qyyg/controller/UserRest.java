package cn.bysj.yty.qyyg.controller;

import cn.bysj.yty.qyyg.common.ControllerMapping;
import cn.bysj.yty.qyyg.common.SessionUtil;
import cn.bysj.yty.qyyg.common.UrlMapping;
import cn.bysj.yty.qyyg.domain.Staff;
import cn.bysj.yty.qyyg.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value=ControllerMapping.USER)
public class UserRest {
    private final Logger logger = LoggerFactory.getLogger(UserRest.class);
    @Autowired
    private UserService userService;
    @RequestMapping(value = UrlMapping.LOGIN, method = RequestMethod.POST)
    public JSONObject login(String username, String password) {
        logger.info("登录请求参数====username:"+username+",password:"+password);
        JSONObject rspJson = new JSONObject();
        if(StringUtils.isEmpty(username)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数用户名不能为空");
            return rspJson;
        }
        if(StringUtils.isEmpty(password)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数密码不能为空");
            return rspJson;
        }
        try{
            String userName = userService.isLogin(username, password);
            if(!StringUtils.isEmpty(userName)){
                SessionUtil.setSession("operNo",username);
                rspJson.put("userName",userName);
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","登录成功");
            }else{
                rspJson.put("respCode","9999");
                rspJson.put("respDesc","用户或密码错误");
            }
        }catch(Exception e){
            logger.error("登录异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","登录异常,请联系系统管理员");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.GET_STAFF_INFO, method = RequestMethod.POST)
    public JSONObject getStaffInfo(String operNo) {
        logger.info("查询个人信息====operNo:"+operNo);
        JSONObject rspJson = new JSONObject();
        if(StringUtils.isEmpty(operNo)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数用户名不能为空");
            return rspJson;
        }
        try{
            JSONObject staffJson = userService.getStaffInfo(operNo);
            if(staffJson!=null){
                rspJson.put("staffInfo",staffJson);
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","查询个人信息成功");
            }else{
                rspJson.put("respCode","9999");
                rspJson.put("respDesc","查询个人信息为空");
            }
        }catch(Exception e){
            logger.error("查询个人信息异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询异常,请联系系统管理员");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.UPDATE_PERSON_STAFF_INFO, method = RequestMethod.POST)
    public JSONObject updatePersonStaffInfo(String jsonStr) {
        logger.info("修改个人信息====:"+jsonStr);
        JSONObject rspJson = new JSONObject();
        if(StringUtils.isEmpty(jsonStr)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","修改参数不能为空");
            return rspJson;
        }
        JSONObject jsonObj = JSONObject.parseObject(jsonStr);
        if(!jsonObj.containsKey("phone")){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数phone为空");
        }
        if(!jsonObj.containsKey("remark")){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数remark为空");
        }
        if(!jsonObj.containsKey("email")){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数email为空");
        }
        if(!jsonObj.containsKey("staffId")){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数staffId为空");
        }
        Staff staff = new Staff();
        staff.setStaffId(jsonObj.getInteger("staffId"));
        staff.setRemark(jsonObj.getString("remark"));
        staff.setTelNumber(jsonObj.getLong("phone"));
        staff.setStaffEmail(jsonObj.getString("email"));
        try{
            rspJson = userService.updatePersonalStaffInfo(staff);
        }catch(Exception e){
            logger.error("修改个人信息异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","更新资料异常,请联系系统管理员");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.GET_STAFF_INFO_LIST, method = RequestMethod.POST)
    public JSONObject getStaffInsoList(String jsonStr) {
        logger.info("条件查询员工信息列表====:"+jsonStr);
        JSONObject rspJson = new JSONObject();
        JSONObject jsonObj = JSONObject.parseObject(jsonStr);
        Staff staff = new Staff();
        if(jsonObj.containsKey("department_id") && !StringUtils.isEmpty(jsonObj.getString("department_id"))){
            staff.setDepartmentId(jsonObj.getInteger("department_id"));
        }
        if(jsonObj.containsKey("staff_name") && !StringUtils.isEmpty(jsonObj.getString("staff_name"))){
            staff.setStaffName(jsonObj.getString("staff_name"));
        }
        if(!jsonObj.containsKey("pageNo")){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数pageNo为空！");
            return rspJson;
        }
        if(!jsonObj.containsKey("pageSize")){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数pageSize为空！");
            return rspJson;
        }
        int pageNo = jsonObj.getInteger("pageNo");
        int pageSize = jsonObj.getInteger("pageSize");
        try{
            rspJson = userService.queryStaffInfoByCondition(staff,pageNo,pageSize);
            rspJson.put("respCode","0000");
            rspJson.put("respDesc","查询员工信息列表成功");
        }catch(Exception e){
            logger.error("条件查询员工信息列表异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询员工信息列表异常,请联系系统管理员");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.UPDATE_STAFF_STATE, method = RequestMethod.POST)
    public JSONObject updateStaffState(String operNo,Integer state) {
        logger.info("修改工号状态====:operNo"+operNo+"==state:"+state);
        JSONObject rspJson = new JSONObject();
        if(StringUtils.isEmpty(operNo)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数operNo不能为空");
            return rspJson;
        }
        if(state==null){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","state");
            return rspJson;
        }
        try{
            rspJson = userService.updateStaffInfoState(operNo,state);
            rspJson.put("respCode","0000");
            rspJson.put("respDesc","修改工号状态成功");
        }catch(Exception e){
            logger.error("修改工号状态异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","修改工号状态异常,请联系系统管理员");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.ADD_STAFF, method = RequestMethod.POST)
    public JSONObject addStaff(String jsonStr) {
        logger.info("新增员工====:jsonStr"+jsonStr);
        JSONObject rspJson = new JSONObject();
        JSONObject json = null;
        try{
            json = JSONObject.parseObject(jsonStr);
        }catch(Exception e){
            logger.error("新增员工信息异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","新增员工信息异常,请确认传参是否为json格式");
        }
        Staff staff = new Staff();
        staff.setJobId(json.getInteger("job_id"));
        staff.setDepartmentId(json.getInteger("department_id"));
        staff.setStaffName(json.getString("staff_name"));
        staff.setStaffGender(json.getInteger("gender"));
        staff.setStaffBirthday(json.getDate("birthday"));
        staff.setStaffEmail(json.getString("email"));
        staff.setTelNumber(json.getLong("tel_number"));
        staff.setNativePlace(json.getString("native_place"));
        staff.setEducation(json.getInteger("education"));
        staff.setMajor(json.getString("major"));
        staff.setInductionTime(json.getDate("induction_time"));
        staff.setOperNo(json.getString("oper_no"));
        try{
            rspJson = userService.insertStaff(staff);
        }catch(Exception e){
            logger.error("修改工号状态异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","新增员工信息异常,请联系系统管理员");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.GET_STAFF_NOW_DATE, method = RequestMethod.POST)
    public JSONObject getStaffListByNowDate(int pageNo,int pageSize) {
        JSONObject rspJson = new JSONObject();
        try{
            rspJson = userService.getStaffListByNowDate(pageNo, pageSize);
            rspJson.put("respCode","0000");
            rspJson.put("respDesc","查询当天生日员工信息列表成功");
        }catch(Exception e){
            logger.error("查询当天生日员工信息列表异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","查询当天生日员工信息列表异常,请联系系统管理员");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.MODIFY_PWD, method = RequestMethod.POST)
    public JSONObject modifyPwd(String operNo,String oldPwd,String password) {
        logger.info("修改工号密码====:operNo"+operNo+"==oldPwd:"+oldPwd+"===password"+password);
        JSONObject rspJson = new JSONObject();
        if(StringUtils.isEmpty(operNo)){
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","参数operNo不能为空");
            return rspJson;
        }
        try{
            rspJson = userService.updatePwd(operNo, oldPwd, password);
        }catch(Exception e){
            logger.error("修改工号密码异常：",e);
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","修改工号密码异常,请联系系统管理员");
        }
        return rspJson;
    }
}
