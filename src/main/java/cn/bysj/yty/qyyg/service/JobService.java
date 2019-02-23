package cn.bysj.yty.qyyg.service;

import com.alibaba.fastjson.JSONObject;

public interface JobService {
    /**
     * 新增工种
     *
     * @param jobName
     * @param jobDesc
     * @return
     * @throws Exception
     */
    JSONObject addJob(String jobName, String jobDesc) throws Exception;

    /**
     * 查询工种列表
     *
     * @param jobName
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    JSONObject qryJobByCondition(String jobName,String state, Integer pageNo, Integer pageSize) throws Exception;
}
