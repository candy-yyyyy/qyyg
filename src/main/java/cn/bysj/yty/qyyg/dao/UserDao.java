package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<Staff, Long> {
    @Query(value = "select * from t_staff where oper_no=?1 and password=?2", nativeQuery = true)
    public List<Staff> login(String username, String password);

    @Query(value = "select * from t_staff where oper_no=?1",nativeQuery = true)
    public Staff getStaffInfo(String operNo);

    @Modifying(clearAutomatically = true)
    @Query(value = "update t_staff set remark=:#{#staff.remark},tel_number=:#{#staff.telNumber},staff_email=:#{#staff.staffEmail} where staff_id=:#{#staff.staffId}",nativeQuery = true)
    public int updatePersonalStaffInfo(@Param("staff")Staff staff);

   /* @Query(value = "select count(*) from t_staff where state=0 and ?1",nativeQuery =  true)
    public int queryStaffInfoByConditionCount(String appendSqlStr);*/

   /* @Query(value = "select * from t_staff where state=0 #{#} limit ?2,?3",nativeQuery =  true)
    public List<Staff> queryStaffInfoByCondition(String appendSqlStr,int pageOffset, int pageSize);*/
}
