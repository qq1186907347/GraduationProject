package graduation.money.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.money.dto.BalanceUser;

import java.util.List;

public interface BalanceUserMapper extends Mapper<BalanceUser>{

    List<BalanceUser> selectUserBalance(BalanceUser dto);
}