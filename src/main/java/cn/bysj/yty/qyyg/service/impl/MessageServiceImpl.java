package cn.bysj.yty.qyyg.service.impl;

import cn.bysj.yty.qyyg.dao.MessageDao;
import cn.bysj.yty.qyyg.dao.UserDao;
import cn.bysj.yty.qyyg.domain.Message;
import cn.bysj.yty.qyyg.domain.Staff;
import cn.bysj.yty.qyyg.service.MessageService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@Service("MessageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;
    @Override
    public JSONObject getMessageListInfo(int pageNo, int pageSize) throws Exception {
        JSONObject rspJson = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int total = messageDao.getMessageCount();
        if(total>0){
            int pageOffset = (pageNo-1)*pageSize;
            List<Message> messageList = messageDao.getMessageListInfo(pageOffset,pageSize);
            JSONArray messageArr = new JSONArray();
            if(messageList!=null && messageList.size()>0){
                for(Message message : messageList){
                    message.setCreateTime(sdf.format(sdf.parse(message.getCreateTime())));
                    JSONObject messageObj = (JSONObject)JSON.toJSON(message);
                    Staff staff = userDao.getStaffInfo(message.getOperNo());
                    if(staff!=null){
                        messageObj.put("staffName",staff.getStaffName());
                    }
                    messageArr.add(messageObj);
                }
            }
            rspJson.put("total",total);
            rspJson.put("messageList",messageArr);
        }else{
            rspJson.put("total",total);
        }
        return rspJson;
    }

    @Override
    @Transactional
    public Boolean addMessage(Message message) throws Exception {
        int flag = messageDao.addMessage(message);
        if(flag>0){
            return true;
        }else{
            return false;
        }
    }
}
