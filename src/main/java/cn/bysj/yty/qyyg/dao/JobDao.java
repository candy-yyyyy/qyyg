package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JobDao extends JpaRepository<Job, Long> {
    @Query(value = "select * from t_job where job_id=?1 and state=0", nativeQuery = true)
    Job getJobNameById(int jobId);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into t_job(job_name,job_desc,state) values(?1,?2,0)", nativeQuery = true)
    int addJob(String jobName, String jobDesc);

    @Query(value = "select * from t_job where job_name=?1", nativeQuery = true)
    Job getJobNameByJobName(String jobName);

    @Modifying(clearAutomatically = true)
    @Query(value = "update t_job set state=?2 where job_id=?1", nativeQuery = true)
    int updateJobState(Integer jobId,Integer state);
}
