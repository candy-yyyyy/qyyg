package cn.bysj.yty.qyyg.service.impl;

import cn.bysj.yty.qyyg.domain.Salary;
import cn.bysj.yty.qyyg.service.SalaryService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Service("SalaryService")
public class SalaryServiceImpl implements SalaryService {
    @PersistenceContext
    private EntityManager em; //注入EntityManager
    @Override
    public JSONObject getSalaryInfoListByOperNo(String operNo, String startMonth, String endMonth, int pageNo, int pageSize) throws Exception {
        JSONObject rspJson = new JSONObject();
        String hqlStr = " from Salary a where 1=1 and a.operNo='"+operNo+"'";
        if(!StringUtils.isEmpty(startMonth)){
            hqlStr += " and str_to_date(a.yearMonth,'%Y-%m')>=str_to_date('"+startMonth+"','%Y-%m')";
        }
        if(!StringUtils.isEmpty(endMonth)){
            hqlStr += " and str_to_date(a.yearMonth,'%Y-%m')<=str_to_date('"+endMonth+"','%Y-%m')";
        }
        Query query = em.createQuery(hqlStr);
        int total = query.getResultList().size();
        if(total>0){
            int pageOffset = (pageNo-1)*pageSize;
            query = em.createQuery(hqlStr).setFirstResult(pageOffset).setMaxResults(pageSize);
            List<Salary> salaryList = query.getResultList();
            JSONArray salaryArr = new JSONArray();
            if(salaryList != null && salaryList.size()>0){
                for (Salary salary:salaryList
                     ) {
                    JSONObject salaryJson = new JSONObject();
                    salaryJson.put("yearMonth",salary.getYearMonth());
                    salaryJson.put("monthMoney",salary.getMonthMoney().toString());
                    salaryJson.put("salaryDesc",salary.getSalaryDesc());
                    salaryArr.add(salaryJson);
                }
            }
            rspJson.put("total",total);
            rspJson.put("staffList", salaryArr);
        }else{
            rspJson.put("total","0");
        }
        return rspJson;
    }

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal AA = new BigDecimal("23.00");
        System.out.println(AA);
    }
}
