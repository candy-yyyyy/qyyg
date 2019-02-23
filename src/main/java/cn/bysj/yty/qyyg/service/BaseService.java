package cn.bysj.yty.qyyg.service;

import com.alibaba.fastjson.JSONArray;

public interface BaseService {
    /**
     * 查询有效的静态参数数据
     *
     * @param entity
     * @return
     * @throws Exception
     */
    JSONArray queryInfoByEntity(String entity) throws Exception;
}
