package cn.bysj.yty.qyyg.service;

import cn.bysj.yty.qyyg.domain.Message;
import com.alibaba.fastjson.JSONObject;

public interface MessageService {
    /**
     * 分页获取留言信息
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public JSONObject getMessageListInfo(int pageNo, int pageSize) throws Exception;

    /**
     * 新增留言记录
     * @param message
     * @return
     * @throws Exception
     */
    public Boolean addMessage(Message message) throws Exception;
}
