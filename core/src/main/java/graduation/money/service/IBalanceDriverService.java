package graduation.money.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.money.dto.BalanceDriver;

import java.util.List;

public interface IBalanceDriverService extends IBaseService<BalanceDriver>, ProxySelf<IBalanceDriverService>{

    List<BalanceDriver> selectDriverBalance(IRequest requestContext, BalanceDriver dto, int page, int pageSize);

    Float getDriverBalance(BalanceDriver balanceDriver);
}