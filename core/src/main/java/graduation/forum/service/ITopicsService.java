package graduation.forum.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.forum.dto.Topics;

import java.util.List;

public interface ITopicsService extends IBaseService<Topics>, ProxySelf<ITopicsService>{

    List<Topics> selectTopics(IRequest requestContext, Topics dto, int page, int pageSize);

    void addTopics(Topics dto);

    void setTopicsTime(Topics topics);
}