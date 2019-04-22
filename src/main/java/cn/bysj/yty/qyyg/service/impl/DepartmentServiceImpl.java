package cn.bysj.yty.qyyg.service.impl;

import cn.bysj.yty.qyyg.dao.DepartmentDao;
import cn.bysj.yty.qyyg.domain.Department;
import cn.bysj.yty.qyyg.service.DepartmentService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service("DepartmentService")
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;
    @PersistenceContext
    private EntityManager em; //注入EntityManager
    @Override
    @Transactional
    public JSONObject addDepartment(String departmentName, String departmentDesc,String departmentManager) throws Exception {
        JSONObject rspJson = new JSONObject();
        Department department = departmentDao.getDepartmentByDepartName(departmentName);
        if(department==null){
            int flag = departmentDao.addDepartment(departmentName, departmentDesc,departmentManager);
            if(flag>0){
                rspJson.put("respCode","0000");
                rspJson.put("respDesc","新增部门成功！");
            }else{
                rspJson.put("respCode","9999");
                rspJson.put("respDesc","新增部门失败！");
            }
        }else{
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","已存在的部门名称！");
        }
        return rspJson;
    }

    @Override
    public JSONObject queryDepartByCondition(String departmentName, String state, Integer pageNo, Integer pageSize) throws Exception {
        JSONObject rspJson = new JSONObject();
        String hqlStr = " from Department a where 1=1";
        if(!StringUtils.isEmpty(departmentName)){
            hqlStr += " and a.departmentName like '%"+departmentName+"%'";
        }
        if(!StringUtils.isEmpty(state)){
            hqlStr += " and a.state="+Integer.parseInt(state);
        }
        Query query = em.createQuery(hqlStr);
        int total = query.getResultList().size();
        if(total>0){
            int pageOffset = (pageNo-1)*pageSize;
            query = em.createQuery(hqlStr).setFirstResult(pageOffset).setMaxResults(pageSize);
            List<Department> departInfoList = query.getResultList();
            rspJson.put("total",total);
            rspJson.put("departList", departInfoList);
        }else{
            rspJson.put("total",0);
        }
        return rspJson;
    }

    @Override
    @Transactional
    public JSONObject updateDepartState(Integer departId, Integer state) throws Exception {
        JSONObject rspJson = new JSONObject();
        int flag = departmentDao.updateDepartState(departId, state);
        if(flag>0){
            rspJson.put("respCode","0000");
            rspJson.put("respDesc","修改部门状态成功！");
        }else{
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","修改部门状态失败！");
        }
        return rspJson;
    }
}
