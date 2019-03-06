package graduation.money.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.money.dto.AddMoney;

import java.util.List;

public interface IAddMoneyService extends IBaseService<AddMoney>, ProxySelf<IAddMoneyService>{

    List<AddMoney> selectAddMoney(IRequest requestContext, AddMoney dto, int page, int pageSize);
}