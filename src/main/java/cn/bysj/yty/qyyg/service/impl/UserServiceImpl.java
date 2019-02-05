package cn.bysj.yty.qyyg.service.impl;

import cn.bysj.yty.qyyg.dao.DepartmentDao;
import cn.bysj.yty.qyyg.dao.JobDao;
import cn.bysj.yty.qyyg.dao.UserDao;
import cn.bysj.yty.qyyg.domain.Department;
import cn.bysj.yty.qyyg.domain.Job;
import cn.bysj.yty.qyyg.domain.Staff;
import cn.bysj.yty.qyyg.service.UserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private DepartmentDao departmentDao;
    @PersistenceContext
    private EntityManager em; //注入EntityManager
    @Override
    public String isLogin(String username, String password) throws Exception {
        List<Staff> staffList = userDao.login(username,password);
        String userName = "";
        if(staffList !=null && staffList.size()>0){
            userName = staffList.get(0).getStaffName();
        }
        return userName;
    }

    @Override
    public JSONObject getStaffInfo(String operNo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Staff staff = userDao.getStaffInfo(operNo);
        if(staff != null){
            JSONObject staffJson = new JSONObject();
            staffJson.put("staffName", StringUtils.isEmpty(staff.getStaffName())?"":staff.getStaffName());    // 员工姓名
            // 职位
            if(staff.getJobId()!=null){
                int jobId = staff.getJobId();
                Job job = jobDao.getJobNameById(jobId);
                if(job!=null){
                    staffJson.put("job",StringUtils.isEmpty(job.getJobName())?"":job.getJobName());
                }else{
                    staffJson.put("job","");
                }
            }else{
                staffJson.put("job","");
            }
            // 部门
            if(staff.getDepartmentId()!=null){
                int departmentId = staff.getDepartmentId();
                Department department = departmentDao.getDepartmentById(departmentId);
                if(department!=null){
                    staffJson.put("department",StringUtils.isEmpty(department.getDepartmentName())?"":department.getDepartmentName());
                }else{
                    staffJson.put("department","");
                }
            }else{
                staffJson.put("department","");
            }
            staffJson.put("birthday",staff.getStaffBirthday()==null?"":sdf.format(staff.getStaffBirthday())); // 生日
            // 性别 翻译 0 男 1 女
            if(staff.getStaffGender()!=null){
                if(staff.getStaffGender()==0){
                    staffJson.put("gender","男");
                }else if(staff.getStaffGender()==1){
                    staffJson.put("gender","女");
                }else{
                    staffJson.put("gender","");
                }
            }else{
                staffJson.put("gender","");
            }
            staffJson.put("nativePlace",StringUtils.isEmpty(staff.getNativePlace())?"":staff.getNativePlace()); // 籍贯
            staffJson.put("major",StringUtils.isEmpty(staff.getMajor())?"":staff.getMajor());    // 专业
            staffJson.put("inductionTime",staff.getInductionTime()==null?"":sdf.format(staff.getInductionTime()));    // 入职时间
            staffJson.put("email",StringUtils.isEmpty(staff.getStaffEmail())?"":staff.getStaffEmail()); // 邮箱
            staffJson.put("telNumber",staff.getTelNumber()==null?"":staff.getTelNumber());   // 手机号
            staffJson.put("remark",StringUtils.isEmpty(staff.getRemark())?"":staff.getRemark());    // 个性签名
            staffJson.put("operNo",StringUtils.isEmpty(staff.getOperNo())?"":staff.getOperNo());    // 工号
            staffJson.put("staffId",staff.getStaffId());
            if(staff.getEducation()!=null){
                if(staff.getEducation()==0){
                    staffJson.put("education","高中");
                }else if(staff.getEducation()==1){
                    staffJson.put("education","本科");
                }else if(staff.getEducation()==2){
                    staffJson.put("education","硕士");
                }else if(staff.getEducation()==3){
                    staffJson.put("education","博士");
                }else{
                    staffJson.put("education","");
                }
            }else{
                staffJson.put("education","");
            }
            return staffJson;
        }
        return null;
    }

    @Override
    @Transactional
    public JSONObject updatePersonalStaffInfo(Staff staff) throws Exception {
        JSONObject rspJson = new JSONObject();
        int i = userDao.updatePersonalStaffInfo(staff);
        if(i>=0){
            rspJson.put("respCode","0000");
            rspJson.put("respDesc","修改个人信息成功");
        }else{
            rspJson.put("respCode","9999");
            rspJson.put("respDesc","修改个人信息失败");
        }
        return rspJson;
    }

    @Override
    public JSONObject queryStaffInfoByCondition(Staff staff, int pageNo, int pageSize) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject rspJson = new JSONObject();
        String hqlStr = " from Staff a where 1=1";
        if(staff.getDepartmentId()!=null){ // 部门
            hqlStr += " and a.departmentId="+staff.getDepartmentId();
        }
        if(!StringUtils.isEmpty(staff.getStaffName())){   // 名字
            hqlStr += " and a.staffName like '%"+staff.getStaffName()+"%'";
        }
        Query query = em.createQuery(hqlStr);
        int total = query.getResultList().size();
        if(total>0){
            JSONArray staffInfoArr = new JSONArray();
            int pageOffset = (pageNo-1)*pageSize;
            query = em.createQuery(hqlStr).setFirstResult(pageOffset).setMaxResults(pageSize);
            @SuppressWarnings("unchecked")
            List<Staff> staffInfoList = query.getResultList();
            if(staffInfoList!=null && staffInfoList.size()>0){
                for (Staff staffObj:staffInfoList
                     ) {
                    JSONObject staffJson = new JSONObject();
                    staffJson.put("staffName", StringUtils.isEmpty(staffObj.getStaffName())?"":staffObj.getStaffName());    // 员工姓名
                    // 职位
                    if(staff.getJobId()!=null){
                        int jobId = staffObj.getJobId();
                        Job job = jobDao.getJobNameById(jobId);
                        if(job!=null){
                            staffJson.put("job",StringUtils.isEmpty(job.getJobName())?"":job.getJobName());
                        }else{
                            staffJson.put("job","");
                        }
                    }else{
                        staffJson.put("job","");
                    }
                    // 部门
                    if(staffObj.getDepartmentId()!=null){
                        int departmentId = staffObj.getDepartmentId();
                        Department department = departmentDao.getDepartmentById(departmentId);
                        if(department!=null){
                            staffJson.put("department",StringUtils.isEmpty(department.getDepartmentName())?"":department.getDepartmentName());
                        }else{
                            staffJson.put("department","");
                        }
                    }else{
                        staffJson.put("department","");
                    }
                    staffJson.put("birthday",staffObj.getStaffBirthday()==null?"":sdf.format(staffObj.getStaffBirthday())); // 生日
                    // 性别 翻译 0 男 1 女
                    if(staffObj.getStaffGender()!=null){
                        if(staffObj.getStaffGender()==0){
                            staffJson.put("gender","男");
                        }else if(staffObj.getStaffGender()==1){
                            staffJson.put("gender","女");
                        }else{
                            staffJson.put("gender","");
                        }
                    }else{
                        staffJson.put("gender","");
                    }
                    staffJson.put("nativePlace",StringUtils.isEmpty(staffObj.getNativePlace())?"":staffObj.getNativePlace()); // 籍贯
                    staffJson.put("major",StringUtils.isEmpty(staffObj.getMajor())?"":staffObj.getMajor());    // 专业
                    staffJson.put("inductionTime",staffObj.getInductionTime()==null?"":sdf.format(staffObj.getInductionTime()));    // 入职时间
                    staffJson.put("email",StringUtils.isEmpty(staffObj.getStaffEmail())?"":staffObj.getStaffEmail()); // 邮箱
                    staffJson.put("telNumber",staffObj.getTelNumber()==null?"":staffObj.getTelNumber());   // 手机号
                    staffJson.put("remark",StringUtils.isEmpty(staffObj.getRemark())?"":staffObj.getRemark());    // 个性签名
                    staffJson.put("operNo",StringUtils.isEmpty(staffObj.getOperNo())?"":staffObj.getOperNo());    // 工号
                    staffJson.put("staffId",staffObj.getStaffId());
                    if(staffObj.getEducation()!=null){
                        if(staffObj.getEducation()==0){
                            staffJson.put("education","高中");
                        }else if(staffObj.getEducation()==1){
                            staffJson.put("education","本科");
                        }else if(staffObj.getEducation()==2){
                            staffJson.put("education","硕士");
                        }else if(staffObj.getEducation()==3){
                            staffJson.put("education","博士");
                        }else{
                            staffJson.put("education","");
                        }
                    }else{
                        staffJson.put("education","");
                    }
                    staffInfoArr.add(staffJson);
                }
            }
            rspJson.put("total",total);
            rspJson.put("staffList",staffInfoArr);
        }else{
            rspJson.put("total",total);
        }
        return rspJson;
    }

}
