package graduation.credit.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.credit.mapper.CreditUserMapper;
import graduation.money.dto.BalanceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.credit.dto.CreditUser;
import graduation.credit.service.ICreditUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CreditUserServiceImpl extends BaseServiceImpl<CreditUser> implements ICreditUserService{
    @Autowired
    CreditUserMapper creditUserMapper;

    @Override
    public Float getUserCredit(CreditUser creditUser) {
        List<CreditUser> creditUserList = creditUserMapper.selectCreditUser(creditUser);
        Float credit = Float.valueOf(0);
        for (CreditUser creditUser1 : creditUserList) {
            if (creditUser1.getCreditType() == 0L) {
                credit = credit + Float.valueOf(creditUser1.getNum());
            } else if (creditUser1.getCreditType()== 1L) {
                credit = credit - Float.valueOf(creditUser1.getNum());
            }
        }
        return credit;
    }

    @Override
    public void addCredit(CreditUser dto) {
        creditUserMapper.addCredit(dto);
    }

    @Override
    public List<CreditUser> selectCreditUser(IRequest requestContext, CreditUser dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return creditUserMapper.selectCreditUser(dto);
    }
}