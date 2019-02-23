package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<Staff, Long> {
    @Query(value = "select * from t_staff where oper_no=?1 and password=?2", nativeQuery = true)
    List<Staff> login(String username, String password);

    @Query(value = "select * from t_staff where oper_no=?1", nativeQuery = true)
    Staff getStaffInfo(String operNo);

    @Modifying(clearAutomatically = true)
    @Query(value = "update t_staff set remark=:#{#staff.remark},tel_number=:#{#staff.telNumber},staff_email=:#{#staff.staffEmail} where staff_id=:#{#staff.staffId}", nativeQuery = true)
    int updatePersonalStaffInfo(@Param("staff") Staff staff);

   /* @Query(value = "select count(*) from t_staff where state=0 and ?1",nativeQuery =  true)
     int queryStaffInfoByConditionCount(String appendSqlStr);*/

    /* @Query(value = "select * from t_staff where state=0 #{#} limit ?2,?3",nativeQuery =  true)
      List<Staff> queryStaffInfoByCondition(String appendSqlStr,int pageOffset, int pageSize);*/
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_staff set state=?2 where oper_no=?1", nativeQuery = true)
    int updateStaffInfoState(String operNo, Integer state);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into t_staff(department_id,job_id,staff_name,staff_gender,staff_birthday,staff_email,tel_number,native_place,education,major,induction_time,oper_no,password,role_id,state) " +
            "values(:#{#staff.departmentId},:#{#staff.jobId},:#{#staff.staffName},:#{#staff.staffGender},:#{#staff.staffBirthday},:#{#staff.staffEmail},:#{#staff.telNumber},:#{#staff.nativePlace},:#{#staff.education},:#{#staff.major}," +
            ":#{#staff.inductionTime},:#{#staff.operNo},:#{#staff.password},:#{#staff.roleId},:#{#staff.state})", nativeQuery = true)
    int insertStaff(@Param("staff") Staff staff);
}
