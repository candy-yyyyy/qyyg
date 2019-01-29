package cn.bysj.yty.qyyg.service;

import cn.bysj.yty.qyyg.domain.Staff;
import com.alibaba.fastjson.JSONObject;

public interface UserService {
    public String isLogin(String username,String password) throws Exception;

    public JSONObject getStaffInfo(String operNo) throws Exception;

    public JSONObject updatePersonalStaffInfo(Staff staff) throws Exception;
}
