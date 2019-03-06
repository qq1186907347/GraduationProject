package graduation.money.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.money.dto.AddMoney;

import java.util.List;

public interface AddMoneyMapper extends Mapper<AddMoney>{

    List<AddMoney> selectAddMoney(AddMoney dto);
}