package graduation.money.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.money.dto.BalanceUser;

import java.util.List;

public interface IBalanceUserService extends IBaseService<BalanceUser>, ProxySelf<IBalanceUserService>{

    /**获取余额*/
    Float getUserBalance(BalanceUser dto);

    List<BalanceUser> selectUserBalance(IRequest requestContext, BalanceUser dto, int page, int pageSize);
}