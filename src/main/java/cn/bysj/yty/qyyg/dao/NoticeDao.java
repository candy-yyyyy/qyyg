package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeDao extends JpaRepository<Notice, Long> {
    @Query(value = "select * from t_notice where state=0 order by create_time desc", nativeQuery = true)
    public List<Notice> getNoticeList();
}
