package cn.bysj.yty.qyyg.service.impl;

import cn.bysj.yty.qyyg.dao.AttendanceDao;
import cn.bysj.yty.qyyg.domain.Attendance;
import cn.bysj.yty.qyyg.service.AttendanceService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service("AttendanceService")
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceDao attendanceDao;
    @Override
    public JSONArray getAttendanceByOperNo(String operNo) throws Exception {
        List<Attendance> attendanceList = attendanceDao.getAttendanceByOperNo(operNo);
        JSONArray attendanceArr = new JSONArray();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        if(attendanceList!=null && attendanceList.size()>0){
            for (Attendance attendance:attendanceList
                 ) {
                // 判断一条考勤记录的起始时间和结束时间不为空 并且在同一天
                if(attendance.getAttendanceStime()!=null && attendance.getAttendanceEtime()!=null
                && sdf.format(attendance.getAttendanceStime()).equals(sdf.format(attendance.getAttendanceEtime()))){
                    JSONObject attendanceObj = new JSONObject();
                    calendar.setTime(attendance.getAttendanceStime());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH)+1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    attendanceObj.put("year",year);
                    attendanceObj.put("month",month);
                    attendanceObj.put("day",day);
                    attendanceArr.add(attendanceObj);
                }
            }
        }
        return attendanceArr;
    }

    @Override
    @Transactional
    public Boolean insertAttendance(String operNo) throws Exception {
        int flag =  attendanceDao.insertAttendance(operNo);
        if(flag>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Attendance getAttendanceToday(String operNo) throws Exception {
        List<Attendance> attendanceList = attendanceDao.getAttendanceToday(operNo);
        if(attendanceList!=null && attendanceList.size()>0){
            return attendanceList.get(0);
        }else{
            return null;
        }
    }

    @Override
    @Transactional
    public Boolean updateAttendance(Integer attendanceId) throws Exception {
        int flag = attendanceDao.updateAttendance(attendanceId);
        if(flag>0){
            return true;
        }else{
            return false;
        }
    }

}
