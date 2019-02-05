package cn.bysj.yty.qyyg.service.impl;

import cn.bysj.yty.qyyg.service.BaseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service("BaseService")
public class BaseServiceImpl implements BaseService {
    @PersistenceContext
    private EntityManager em; //注入EntityManager
    @Override
    public JSONArray queryInfoByEntity(String entity) throws Exception {
        String hqlStr = " from "+entity+" where state=0";
        Query query = em.createQuery(hqlStr);
        List list = query.getResultList();
        if(list!=null && list.size()>0){
            return (JSONArray)JSON.toJSON(list);
        }
        return null;
    }
}
