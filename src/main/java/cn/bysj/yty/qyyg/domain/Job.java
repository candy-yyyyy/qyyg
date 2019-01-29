package cn.bysj.yty.qyyg.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="t_job")
public class Job implements Serializable {
    private static final long serialVersionUID = 5867641816414539803L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Integer jobId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_desc")
    private String jobDesc;

    @Column(name = "state")
    private Integer state;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", jobName='" + jobName + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                ", state=" + state +
                '}';
    }
}
