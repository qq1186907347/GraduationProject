package graduation.credit.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.credit.dto.CreditUser;

import java.util.List;

public interface ICreditUserService extends IBaseService<CreditUser>, ProxySelf<ICreditUserService>{

    Float getUserCredit(CreditUser creditUser);

    void addCredit(CreditUser dto);

    List<CreditUser> selectCreditUser(IRequest requestContext, CreditUser dto, int page, int pageSize);
}