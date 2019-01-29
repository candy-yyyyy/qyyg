package cn.bysj.yty.qyyg.service.impl;

import cn.bysj.yty.qyyg.dao.NoticeDao;
import cn.bysj.yty.qyyg.domain.Notice;
import cn.bysj.yty.qyyg.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Override
    public List<Notice> getNoticeList() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Notice> noticeList = noticeDao.getNoticeList();
        if(noticeList!=null && noticeList.size()>0){
            for (Notice notice:noticeList) {
                notice.setCreateTime(sdf.format(sdf.parse(notice.getCreateTime())));
            }
        }
        return noticeList;
    }
}
