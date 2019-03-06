package graduation.money.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.money.mapper.BalanceUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.money.dto.BalanceUser;
import graduation.money.service.IBalanceUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceUserServiceImpl extends BaseServiceImpl<BalanceUser> implements IBalanceUserService {

    @Autowired
    BalanceUserMapper balanceUserMapper;

    @Override
    public Float getUserBalance(BalanceUser dto) {
        List<BalanceUser> balanceUsers = balanceUserMapper.selectUserBalance(dto);
        Float sum = Float.valueOf(0);
        for (BalanceUser balanceUser : balanceUsers) {
            if (balanceUser.getType() == 0L) {
                sum = sum + Float.valueOf(balanceUser.getMoney());
            } else if (balanceUser.getType() == 1L) {
                sum = sum - Float.valueOf(balanceUser.getMoney());
            }

        }
        return sum;
    }

    @Override
    public List<BalanceUser> selectUserBalance(IRequest requestContext, BalanceUser dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return balanceUserMapper.selectUserBalance(dto);
    }
}