package cn.bysj.yty.qyyg.service.impl;

import cn.bysj.yty.qyyg.dao.NoticeDao;
import cn.bysj.yty.qyyg.domain.Notice;
import cn.bysj.yty.qyyg.service.NoticeService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Override
    public JSONObject getNoticeList(int pageNo, int pageSize) throws Exception {
        JSONObject rspJson = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int total = noticeDao.getNoticeListCount();
        if(total>0){
            int pageOffset = (pageNo-1)*pageSize;
            List<Notice> noticeList = noticeDao.getNoticeList(pageOffset,pageSize);
            if(noticeList!=null && noticeList.size()>0){
                for (Notice notice:noticeList) {
                    notice.setCreateTime(sdf.format(sdf.parse(notice.getCreateTime())));
                }
            }
            rspJson.put("total",total);
            rspJson.put("noticeList",noticeList);
        }else{
            rspJson.put("total",0);
        }

        return rspJson;
    }

    @Override
    @Transactional
    public Boolean addNotince(String operNo, String noticeTitle, String noticeContent) throws Exception {
        int flag = noticeDao.addNotice(operNo, noticeTitle, noticeContent);
        if(flag>0){
            return true;
        }else{
            return false;
        }
    }
}
