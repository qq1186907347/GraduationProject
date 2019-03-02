package graduation.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.dto.RefuseResult;

import java.util.List;

public interface IRefuseResultService extends IBaseService<RefuseResult>, ProxySelf<IRefuseResultService>{

    void addResult(RefuseResult dto);

    List<RefuseResult> selectRefuse(RefuseResult dto);
}