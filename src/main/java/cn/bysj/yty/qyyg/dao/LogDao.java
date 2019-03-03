package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LogDao extends JpaRepository<Log, Long> {
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into t_log(oper_no,url,log_time,log_request_info,log_response_info) values(?1,?2,sysdate(),?3,?4)", nativeQuery = true)
    int addLog(String operNo,String url, String reqInfo, String respInfo);
}
