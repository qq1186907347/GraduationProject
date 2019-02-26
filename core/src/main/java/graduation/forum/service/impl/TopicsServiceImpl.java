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

@Service
@Transactional(rollbackFor = Exception.class)
public class TopicsServiceImpl extends BaseServiceImpl<Topics> implements ITopicsService{
    @Autowired
    TopicsMapper topicsMapper;

    @Override
    public List<Topics> selectTopics(IRequest requestContext, Topics dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return topicsMapper.selectTopics(dto);
    }

    @Override
    public void addTopics(Topics dto) {
        topicsMapper.addTopics(dto);
    }

    @Override
    public void setTopicsTime(Topics topics) {
        topicsMapper.setTopicsTime(topics);
    }
}