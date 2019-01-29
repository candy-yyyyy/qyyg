package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobDao extends JpaRepository<Job, Long> {
    @Query(value = "select * from t_job where job_id=?1 and state=0", nativeQuery = true)
    public Job getJobNameById(int jobId);
}
