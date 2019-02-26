package graduation.forum.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.forum.dto.TopicsReply;

import java.util.List;

public interface ITopicsReplyService extends IBaseService<TopicsReply>, ProxySelf<ITopicsReplyService>{

    List<TopicsReply> selectReply(IRequest requestContext, TopicsReply dto, int page, int pageSize);

    void addReply(TopicsReply dto);
}