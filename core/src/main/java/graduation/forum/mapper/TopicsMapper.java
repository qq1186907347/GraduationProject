package graduation.forum.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.forum.dto.Topics;

import java.util.List;

public interface TopicsMapper extends Mapper<Topics>{

    List<Topics> selectTopics(Topics dto);

    void addTopics(Topics dto);

    void setTopicsTime(Topics topics);
}