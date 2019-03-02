package graduation.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.RefuseResult;

import java.util.List;

public interface RefuseResultMapper extends Mapper<RefuseResult>{

    void addResult(RefuseResult dto);

    List<RefuseResult> selectRefuse(RefuseResult dto);
}