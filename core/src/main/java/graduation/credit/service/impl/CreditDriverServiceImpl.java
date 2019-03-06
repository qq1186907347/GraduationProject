package graduation.credit.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.credit.mapper.CreditDriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.credit.dto.CreditDriver;
import graduation.credit.service.ICreditDriverService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CreditDriverServiceImpl extends BaseServiceImpl<CreditDriver> implements ICreditDriverService{
    @Autowired
    CreditDriverMapper creditDriverMapper;

    @Override
    public void addCredit(CreditDriver creditDriver) {
        creditDriverMapper.addCredit(creditDriver);
    }

    @Override
    public List<CreditDriver> selectCreditDriver(IRequest requestContext, CreditDriver dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return creditDriverMapper.selectCreditDriver(dto);
    }

    @Override
    public Float getDriverCredit(CreditDriver creditDriver) {
        List<CreditDriver> creditDriverList = creditDriverMapper.selectCreditDriver(creditDriver);
        Float credit = Float.valueOf(0);
        for (CreditDriver creditDriver1 : creditDriverList) {
            if (creditDriver1.getCreditType() == 0L) {
                credit = credit + Float.valueOf(creditDriver1.getNum());
            } else if (creditDriver1.getCreditType()== 1L) {
                credit = credit - Float.valueOf(creditDriver1.getNum());
            }
        }
        return credit;
    }
}