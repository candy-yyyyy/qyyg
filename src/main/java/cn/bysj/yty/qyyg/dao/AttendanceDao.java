package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttendanceDao extends JpaRepository<Attendance, Long> {

    @Query(value = "select * from t_attendance where oper_no=?1", nativeQuery = true)
    List<Attendance> getAttendanceByOperNo(String operNo);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into t_attendance(oper_no,attendance_stime) values(?1,sysdate())", nativeQuery = true)
    int insertAttendance(String operNo);

    @Query(value = "select * from t_attendance where oper_no=?1 and DATE_FORMAT(NOW(),'%m-%d-%Y')=DATE_FORMAT(attendance_stime,'%m-%d-%Y')", nativeQuery = true)
    List<Attendance> getAttendanceToday(String operNo);

    @Modifying(clearAutomatically = true)
    @Query(value = "update t_attendance set attendance_etime=sysdate() where attendance_id=?1", nativeQuery = true)
    int updateAttendance(Integer attendanceId);
}
