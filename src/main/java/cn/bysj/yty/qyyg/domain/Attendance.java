package cn.bysj.yty.qyyg.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="t_attendance")
public class Attendance implements Serializable {
    private static final long serialVersionUID = -6198416940528621274L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Integer attendanceId;

    @Column(name = "oper_no")
    private String operNo;

    @Column(name = "attendance_stime")
    private Date attendanceStime;

    @Column(name = "attendance_etime")
    private Date attendanceEtime;

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public Date getAttendanceStime() {
        return attendanceStime;
    }

    public void setAttendanceStime(Date attendanceStime) {
        this.attendanceStime = attendanceStime;
    }

    public Date getAttendanceEtime() {
        return attendanceEtime;
    }

    public void setAttendanceEtime(Date attendanceEtime) {
        this.attendanceEtime = attendanceEtime;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceId=" + attendanceId +
                ", operNo='" + operNo + '\'' +
                ", attendanceStime=" + attendanceStime +
                ", attendanceEtime=" + attendanceEtime +
                '}';
    }
}
