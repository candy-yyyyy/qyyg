package cn.bysj.yty.qyyg.controller;


import cn.bysj.yty.qyyg.common.ControllerMapping;
import cn.bysj.yty.qyyg.common.UrlMapping;
import cn.bysj.yty.qyyg.domain.Attendance;
import cn.bysj.yty.qyyg.service.AttendanceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping(value = ControllerMapping.ATTENDANCE)
public class AttendanceRest {
    private final Logger logger = LoggerFactory.getLogger(AttendanceRest.class);

    @Autowired
    private AttendanceService attendanceService;

    @RequestMapping(value = UrlMapping.GET_ATTENDANCE_LIST, method = RequestMethod.POST)
    public JSONObject getAttendanceListByOperNo(String operNo) {
        logger.info("查询考勤记录参数====operNo:" + operNo);
        JSONObject rspJson = new JSONObject();
        try {
            JSONArray arr = attendanceService.getAttendanceByOperNo(operNo);
            if (arr != null && arr.size() > 0) {
                rspJson.put("args", arr);
                rspJson.put("respCode", "0000");
                rspJson.put("respDesc", "查询考勤记录成功");
            } else {
                rspJson.put("respCode", "0000");
                rspJson.put("respDesc", "查询考勤记录为空");
            }
        } catch (Exception e) {
            logger.error("查询考勤记录异常：", e);
            rspJson.put("respCode", "9999");
            rspJson.put("respDesc", "查询考勤记录异常,请联系系统管理员");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.ADD_ATTENDANCE, method = RequestMethod.POST)
    public JSONObject addAttendance(String operNo) {
        logger.info("上班打卡考勤参数====operNo:" + operNo);
        JSONObject rspJson = new JSONObject();
        if (StringUtils.isEmpty(operNo)) {
            rspJson.put("respCode", "9999");
            rspJson.put("respDesc", "参数operNo为空！");
            return rspJson;
        }
        try {
            Boolean flag = attendanceService.insertAttendance(operNo);
            if (flag) {
                rspJson.put("respCode", "0000");
                rspJson.put("respDesc", "考勤上班打卡成功！");
            } else {
                rspJson.put("respCode", "9999");
                rspJson.put("respDesc", "考勤上班打卡失败！");
            }
        } catch (Exception e) {
            logger.error("考勤上班打卡异常：", e);
            rspJson.put("respCode", "9999");
            rspJson.put("respDesc", "考勤上班打卡异常,请联系系统管理员");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.GET_ATTENDANCE_TODAY, method = RequestMethod.POST)
    public JSONObject getAttendanceToday(String operNo) {
        logger.info("查询工号当天考勤记录参数====operNo:" + operNo);
        JSONObject rspJson = new JSONObject();
        if (StringUtils.isEmpty(operNo)) {
            rspJson.put("respCode", "9999");
            rspJson.put("respDesc", "参数operNo为空！");
            return rspJson;
        }
        try {
            Attendance attendance = attendanceService.getAttendanceToday(operNo);
            if (attendance != null) {
                rspJson.put("args", JSON.toJSON(attendance));
                rspJson.put("respCode", "0000");
                rspJson.put("respDesc", "查询工号当天考勤记录成功！");
            } else {
                rspJson.put("respCode", "0000");
                rspJson.put("respDesc", "查询工号当天考勤记录为空！");
            }
        } catch (Exception e) {
            logger.error("查询工号当天考勤记录异常：", e);
            rspJson.put("respCode", "9999");
            rspJson.put("respDesc", "考查询工号当天考勤记录异常,请联系系统管理员");
        }
        return rspJson;
    }

    @RequestMapping(value = UrlMapping.UPDATE_ATTENDANCE, method = RequestMethod.POST)
    public JSONObject updateAttendance(String attendanceId) {
        logger.info("下班打卡考勤参数====attendanceId:" + attendanceId);
        JSONObject rspJson = new JSONObject();
        if (StringUtils.isEmpty(attendanceId)) {
            rspJson.put("respCode", "9999");
            rspJson.put("respDesc", "参数attendanceId为空！");
            return rspJson;
        }

        try {
            Boolean flag = attendanceService.updateAttendance(Integer.parseInt(attendanceId));
            if (flag) {
                rspJson.put("respCode", "0000");
                rspJson.put("respDesc", "下班打卡考勤成功！");
            } else {
                rspJson.put("respCode", "9999");
                rspJson.put("respDesc", "下班打卡考勤失败！");
            }
        } catch (Exception e) {
            logger.error("下班打卡考勤异常：", e);
            rspJson.put("respCode", "9999");
            rspJson.put("respDesc", "下班打卡考勤异常,请联系系统管理员");
        }
        return rspJson;
    }
}
