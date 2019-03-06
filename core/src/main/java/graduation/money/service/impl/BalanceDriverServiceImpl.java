package graduation.money.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.money.mapper.BalanceDriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.money.dto.BalanceDriver;
import graduation.money.service.IBalanceDriverService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceDriverServiceImpl extends BaseServiceImpl<BalanceDriver> implements IBalanceDriverService{
    @Autowired
    BalanceDriverMapper balanceDriverMapper;

    @Override
    public List<BalanceDriver> selectDriverBalance(IRequest requestContext, BalanceDriver dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return balanceDriverMapper.selectDriverBalance(dto);
    }

    @Override
    public Float getDriverBalance(BalanceDriver balanceDriver) {
        List<BalanceDriver> balanceDrivers = balanceDriverMapper.selectDriverBalance(balanceDriver);
        Float sum = 0f;
        for (BalanceDriver balanceDriver1 : balanceDrivers) {
            if (balanceDriver1.getType() == 0L) {
                sum = sum + balanceDriver1.getMoney();
            } else if (balanceDriver1.getType() == 1L) {
                sum = sum +balanceDriver1.getMoney();
            }else if (balanceDriver1.getType() == 2L) {
                sum = sum - balanceDriver1.getMoney();
            }

        }
        return sum;
    }
}