package cn.bysj.yty.qyyg.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="t_staff")
public class Staff implements Serializable {

    private static final long serialVersionUID = 4932010364304886544L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "job_id")
    private Integer jobId;

    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "staff_age")
    private Integer staffAge;

    @Column(name = "staff_gender")
    private Integer staffGender;

    @Column(name = "staff_birthday")
    private Date staffBirthday;

    @Column(name = "staff_email")
    private String staffEmail;

    @Column(name = "tel_number")
    private Long telNumber;

    @Column(name = "native_place")
    private String nativePlace;

    @Column(name = "education")
    private Integer education;

    @Column(name = "major")
    private String major;

    @Column(name = "induction_time")
    private Date inductionTime;

    @Column(name = "staff_desc")
    private String staffDesc;

    @Column(name = "oper_no")
    private String operNo;

    @Column(name = "password")
    private String password;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "state")
    private Integer state;

    @Column(name = "remark")
    private String remark;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getStaffAge() {
        return staffAge;
    }

    public void setStaffAge(Integer staffAge) {
        this.staffAge = staffAge;
    }

    public Integer getStaffGender() {
        return staffGender;
    }

    public void setStaffGender(Integer staffGender) {
        this.staffGender = staffGender;
    }

    public Date getStaffBirthday() {
        return staffBirthday;
    }

    public void setStaffBirthday(Date staffBirthday) {
        this.staffBirthday = staffBirthday;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public Long getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(Long telNumber) {
        this.telNumber = telNumber;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Date getInductionTime() {
        return inductionTime;
    }

    public void setInductionTime(Date inductionTime) {
        this.inductionTime = inductionTime;
    }

    public String getStaffDesc() {
        return staffDesc;
    }

    public void setStaffDesc(String staffDesc) {
        this.staffDesc = staffDesc;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + staffId +
                ", departmentId=" + departmentId +
                ", jobId=" + jobId +
                ", staffName='" + staffName + '\'' +
                ", staffAge=" + staffAge +
                ", staffGender=" + staffGender +
                ", staffBirthday=" + staffBirthday +
                ", staffEmail='" + staffEmail + '\'' +
                ", telNumber=" + telNumber +
                ", nativePlace='" + nativePlace + '\'' +
                ", education=" + education +
                ", major='" + major + '\'' +
                ", inductionTime=" + inductionTime +
                ", staffDesc='" + staffDesc + '\'' +
                ", operNo='" + operNo + '\'' +
                ", password='" + password + '\'' +
                ", roleId='" + roleId + '\'' +
                ", state=" + state +
                ", remark='" + remark + '\'' +
                '}';
    }
}
