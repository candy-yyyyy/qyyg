package cn.bysj.yty.qyyg.service;

import com.alibaba.fastjson.JSONObject;

public interface SalaryService {

    /**
     * 查询员工薪资明细列表
     *
     * @param operNo
     * @return
     * @throws Exception
     */
    JSONObject getSalaryInfoListByOperNo(String operNo, String startMonth, String endMonth, int pageNo, int pageSize) throws Exception;
}
