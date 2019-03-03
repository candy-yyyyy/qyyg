package cn.bysj.yty.qyyg.service.impl;

import cn.bysj.yty.qyyg.dao.LogDao;
import cn.bysj.yty.qyyg.domain.Log;
import cn.bysj.yty.qyyg.service.LogService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.List;

@Service("LogService")
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @PersistenceContext
    private EntityManager em; //注入EntityManager
    @Override
    @Transactional
    public void addLog(String operNo, String url, String reqInfo, String respInfo) throws Exception {
        logDao.addLog(operNo, url, reqInfo, respInfo);
    }

    @Override
    public JSONObject qryLogByCondition(String operNo, String startTime, String endTime, int pageNo, int pageSize) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject rspJson = new JSONObject();
        String hqlStr = " from Log a where 1=1";
        if(!StringUtils.isEmpty(operNo)){
            hqlStr += " and a.operNo like '%"+operNo+"%'";
        }
        if(!StringUtils.isEmpty(startTime)){
            hqlStr += " and str_to_date(a.logTime,'%Y-%m-%d %T')>=str_to_date('"+startTime+"','%Y-%m-%d %T')";
        }
        if(!StringUtils.isEmpty(endTime)){
            hqlStr += " and str_to_date(a.logTime,'%Y-%m-%d %T')<=str_to_date('"+endTime+"','%Y-%m-%d %T')";
        }
        hqlStr += " order by a.logTime desc";
        Query query = em.createQuery(hqlStr);
        int total = query.getResultList().size();
        if(total>0){
            int pageOffset = (pageNo-1)*pageSize;
            query = em.createQuery(hqlStr).setFirstResult(pageOffset).setMaxResults(pageSize);
            List<Log> logList = query.getResultList();
            rspJson.put("total",total);
            rspJson.put("logList", logList);
        }else{
            rspJson.put("total","0");
        }
        return rspJson;
    }
}
