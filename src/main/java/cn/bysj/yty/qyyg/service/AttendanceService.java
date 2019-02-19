package cn.bysj.yty.qyyg.service;

import cn.bysj.yty.qyyg.domain.Attendance;
import com.alibaba.fastjson.JSONArray;

public interface AttendanceService {
    /**
     * 根据工号查询考勤信息
     * @param operNo
     * @return
     * @throws Exception
     */
    public JSONArray getAttendanceByOperNo(String operNo) throws  Exception;

    /**
     * 员工考勤上班打卡
     * @param operNo
     * @return
     * @throws Exception
     */
    public Boolean insertAttendance(String operNo) throws Exception;

    /**
     * 查询今天的考勤记录
     * @param operNo
     * @return
     * @throws Exception
     */
    public Attendance getAttendanceToday(String operNo) throws Exception;

    /**
     * 员工考勤下班打卡
     * @param attendanceId
     * @return
     * @throws Exception
     */
    public Boolean updateAttendance(Integer attendanceId) throws  Exception;
}
