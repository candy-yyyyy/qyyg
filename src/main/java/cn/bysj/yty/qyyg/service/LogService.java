package cn.bysj.yty.qyyg.service;

import com.alibaba.fastjson.JSONObject;

public interface LogService {
    /**
     * 记录请求返回日志
     *
     * @param operNo
     * @param reqInfo
     * @param respInfo
     * @throws Exception
     */
    void addLog(String operNo, String url, String reqInfo, String respInfo) throws Exception;

    /**
     * 条件查询日志列表
     * @param operNo
     * @param startMonth
     * @param endMonth
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    JSONObject qryLogByCondition(String operNo,String startTime, String endTime, int pageNo, int pageSize) throws  Exception;
}
