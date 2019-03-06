package graduation.money.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.money.dto.BalanceDriver;

import java.util.List;

public interface BalanceDriverMapper extends Mapper<BalanceDriver>{

    List<BalanceDriver> selectDriverBalance(BalanceDriver dto);
}