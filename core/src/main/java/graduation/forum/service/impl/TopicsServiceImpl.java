package graduation.forum.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.forum.mapper.TopicsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.forum.dto.Topics;
import graduation.forum.service.ITopicsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional(rollbackFor = Exception.class)
public class TopicsServiceImpl extends BaseServiceImpl<Topics> implements ITopicsService{
    @Autowired
    TopicsMapper topicsMapper;
    /*** 定义锁对象*/
    private Lock lock = new ReentrantLock();

    @Override
    public List<Topics> selectTopics(IRequest requestContext, Topics dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return topicsMapper.selectTopics(dto);
    }

    @Override
    public void addTopics(Topics dto) {
        try {
            lock.lock();
            topicsMapper.addTopics(dto);
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }

    @Override
    public void setTopicsTime(Topics topics) {
        topicsMapper.setTopicsTime(topics);
    }
}