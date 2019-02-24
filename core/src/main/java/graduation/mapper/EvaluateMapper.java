package graduation.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.Evaluate;

import java.util.List;

public interface EvaluateMapper extends Mapper<Evaluate>{

    void addEvaluate(Evaluate dto);

    List<Evaluate> selectEvaluate(Evaluate dto);
}