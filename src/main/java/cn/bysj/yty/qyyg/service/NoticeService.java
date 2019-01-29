package cn.bysj.yty.qyyg.service;

import cn.bysj.yty.qyyg.domain.Notice;

import java.util.List;

public interface NoticeService {
    /**
     * 获取公告列表信息
     * @return
     * @throws Exception
     */
    public List<Notice> getNoticeList() throws Exception;
}
