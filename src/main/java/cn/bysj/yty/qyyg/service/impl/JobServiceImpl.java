package cn.bysj.yty.qyyg.service.impl;

import cn.bysj.yty.qyyg.dao.JobDao;
import cn.bysj.yty.qyyg.domain.Job;
import cn.bysj.yty.qyyg.service.JobService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service("JobService")
public class JobServiceImpl implements JobService {
    @Autowired
    private JobDao jobDao;
    @PersistenceContext
    private EntityManager em; //注入EntityManager
    @Override
    @Transactional
    public JSONObject addJob(String jobName, String jobDesc) throws Exception {
        JSONObject rspJson = new JSONObject();
        Job job = jobDao.getJobNameByJobName(jobName);
        if(job==null){
            int flag = jobDao.addJob(jobName, jobDesc);
            if(flag>0){
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","新增工种成功！");
            }else{
                rspJson.put("respCode","9999");
                rspJson.put("respDesc","新增工种失败！");
            }
        }else{
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","已存在的工种名称！");
        }
        return rspJson;
    }

    @Override
    public JSONObject qryJobByCondition(String jobName,String state,Integer pageNo, Integer pageSize) throws Exception {
        JSONObject rspJson = new JSONObject();
        String hqlStr = " from Job a where 1=1";
        if(!StringUtils.isEmpty(jobName)){
            hqlStr += " and a.jobName like '%"+jobName+"%'";
        }
        if(!StringUtils.isEmpty(state)){
            hqlStr += " and a.state="+Integer.parseInt(state);
        }
        Query query = em.createQuery(hqlStr);
        int total = query.getResultList().size();
        if(total>0){
            int pageOffset = (pageNo-1)*pageSize;
            query = em.createQuery(hqlStr).setFirstResult(pageOffset).setMaxResults(pageSize);
            List<Job> jobInfoList = query.getResultList();
            rspJson.put("total",total);
            rspJson.put("jobList", jobInfoList);
        }else{
            rspJson.put("total",0);
        }
        return rspJson;
    }
}
