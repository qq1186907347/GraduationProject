package graduation.forum.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.forum.mapper.TopicsReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.forum.dto.TopicsReply;
import graduation.forum.service.ITopicsReplyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional(rollbackFor = Exception.class)
public class TopicsReplyServiceImpl extends BaseServiceImpl<TopicsReply> implements ITopicsReplyService{
    @Autowired
    TopicsReplyMapper topicsReplyMapper;
    /*** 定义锁对象*/
    private Lock lock = new ReentrantLock();

    @Override
    public List<TopicsReply> selectReply(IRequest requestContext, TopicsReply dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return topicsReplyMapper.selectReply(dto);
    }

    @Override
    public void addReply(TopicsReply dto)
    {
        try {
            lock.lock();
            topicsReplyMapper.addReply(dto);
        }catch (Exception e){
            throw e;
        }finally {
            lock.unlock();
        }
    }
}