package cn.bysj.yty.qyyg.service;

import com.alibaba.fastjson.JSONObject;

public interface NoticeService {
    /**
     * 获取公告列表信息
     *
     * @return
     * @throws Exception
     */
    JSONObject getNoticeList(int pageNo, int pageSize) throws Exception;

    /**
     * 新增公告
     *
     * @param operNo
     * @param noticeTitle
     * @param noticeContent
     * @return
     * @throws Exception
     */
    Boolean addNotince(String operNo, String noticeTitle, String noticeContent) throws Exception;
}
