package cn.bysj.yty.qyyg.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_log")
public class Log implements Serializable {

    private static final long serialVersionUID = -3139743797110955221L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "oper_no")
    private String operNo;

    @Column(name = "url")
    private String url;

    @Column(name = "log_time")
    private Date logTime;

    @Column(name = "log_request_info")
    private String logRequestInfo;

    @Column(name = "log_response_info")
    private String logResponseInfo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getLogRequestInfo() {
        return logRequestInfo;
    }

    public void setLogRequestInfo(String logRequestInfo) {
        this.logRequestInfo = logRequestInfo;
    }

    public String getLogResponseInfo() {
        return logResponseInfo;
    }

    public void setLogResponseInfo(String logResponseInfo) {
        this.logResponseInfo = logResponseInfo;
    }

    @Override
    public String toString() {
        return "Log{" +
                "logId=" + logId +
                ", operNo='" + operNo + '\'' +
                ", url='" + url + '\'' +
                ", logTime=" + logTime +
                ", logRequestInfo='" + logRequestInfo + '\'' +
                ", logResponseInfo='" + logResponseInfo + '\'' +
                '}';
    }
}
