package graduation.credit.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.credit.dto.CreditDriver;

import java.util.List;

public interface ICreditDriverService extends IBaseService<CreditDriver>, ProxySelf<ICreditDriverService>{

    void addCredit(CreditDriver creditDriver);

    List<CreditDriver> selectCreditDriver(IRequest requestContext, CreditDriver dto, int page, int pageSize);

    Float getDriverCredit(CreditDriver creditDriver);
}