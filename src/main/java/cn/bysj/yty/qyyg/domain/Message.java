package cn.bysj.yty.qyyg.domain;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="t_message")
public class Message implements Serializable {
    private static final long serialVersionUID = -2461693410856397947L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "oper_no")
    private String operNo;

    @Column(name = "content")
    private String content;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "state")
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", operNo='" + operNo + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
