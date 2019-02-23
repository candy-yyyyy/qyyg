package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeDao extends JpaRepository<Notice, Long> {
    @Query(value = "select count(*) from t_notice where state=0", nativeQuery = true)
    int getNoticeListCount();

    @Query(value = "select * from t_notice where state=0 order by create_time desc limit ?1,?2", nativeQuery = true)
    List<Notice> getNoticeList(int pageOffset, int pageSize);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into t_notice(notice_title,notice_content,notice_author,create_time,state) values(?2,?3,?1,sysdate(),0)", nativeQuery = true)
    int addNotice(String operNo, String noticeTitle, String noticeContent);
}
