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

@Service
@Transactional(rollbackFor = Exception.class)
public class TopicsReplyServiceImpl extends BaseServiceImpl<TopicsReply> implements ITopicsReplyService{
    @Autowired
    TopicsReplyMapper topicsReplyMapper;

    @Override
    public List<TopicsReply> selectReply(IRequest requestContext, TopicsReply dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return topicsReplyMapper.selectReply(dto);
    }

    @Override
    public void addReply(TopicsReply dto) {
        topicsReplyMapper.addReply(dto);
    }
}