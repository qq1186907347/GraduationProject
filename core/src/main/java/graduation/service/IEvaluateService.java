package graduation.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.dto.Evaluate;

import java.util.List;

public interface IEvaluateService extends IBaseService<Evaluate>, ProxySelf<IEvaluateService>{

    void addEvaluate(Evaluate dto);

    List<Evaluate> selectEvaluate(IRequest requestContext, Evaluate dto, int page, int pageSize);
}