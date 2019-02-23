package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageDao extends JpaRepository<Message, Long> {

    @Query(value = "select * from t_message where state=0 order by create_time desc limit ?1,?2", nativeQuery = true)
    List<Message> getMessageListInfo(int pageOffset, int pageSize);

    @Query(value = "select count(*) from t_message where state=0", nativeQuery = true)
    int getMessageCount();

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into t_message(oper_no,content) values(:#{#message.operNo},:#{#message.content}) ", nativeQuery = true)
    int addMessage(@Param("message") Message message);
}
