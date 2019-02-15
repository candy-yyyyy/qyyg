package cn.bysj.yty.qyyg.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="t_salary")
public class Salary implements Serializable {

    private static final long serialVersionUID = 6797608104676788664L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id")
    private Integer salaryId;

    @Column(name = "oper_no")
    private String operNo;

    @Column(name = "year_month")
    private String yearMonth;

    @Column(name = "month_money")
    private BigDecimal monthMoney;

    @Column(name = "salary_desc")
    private String salaryDesc;

    public Integer getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Integer salaryId) {
        this.salaryId = salaryId;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public BigDecimal getMonthMoney() {
        return monthMoney;
    }

    public void setMonthMoney(BigDecimal monthMoney) {
        this.monthMoney = monthMoney;
    }

    public String getSalaryDesc() {
        return salaryDesc;
    }

    public void setSalaryDesc(String salaryDesc) {
        this.salaryDesc = salaryDesc;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "salaryId=" + salaryId +
                ", operNo='" + operNo + '\'' +
                ", yearMonth='" + yearMonth + '\'' +
                ", monthMoney=" + monthMoney +
                ", salaryDesc='" + salaryDesc + '\'' +
                '}';
    }
}
