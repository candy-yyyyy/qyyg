package cn.bysj.yty.qyyg.service;

import cn.bysj.yty.qyyg.domain.Staff;
import com.alibaba.fastjson.JSONObject;

public interface UserService {
    public String isLogin(String username,String password) throws Exception;

    public JSONObject getStaffInfo(String operNo) throws Exception;

    public JSONObject updatePersonalStaffInfo(Staff staff) throws Exception;

    /**
     * 根据条件查询工号信息列表
     * @param staff
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public JSONObject queryStaffInfoByCondition(Staff staff,int pageNo, int pageSize) throws Exception;

    /**
     * 修改工号状态
     * @param operNo
     * @param state
     * @return
     * @throws Exception
     */
    public JSONObject updateStaffInfoState(String operNo,Integer state) throws  Exception;

    /**
     * 新增工号
     * @param staff
     * @return
     * @throws Exception
     */
    public JSONObject insertStaff(Staff staff) throws Exception;
}
