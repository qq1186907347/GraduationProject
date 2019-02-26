package graduation.forum.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.forum.dto.TopicsReply;

import java.util.List;

public interface TopicsReplyMapper extends Mapper<TopicsReply>{

    List<TopicsReply> selectReply(TopicsReply dto);

    void addReply(TopicsReply dto);
}