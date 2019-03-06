package graduation.credit.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.credit.dto.CreditDriver;

import java.util.List;

public interface CreditDriverMapper extends Mapper<CreditDriver>{

    void addCredit(CreditDriver creditDriver);

    List<CreditDriver> selectCreditDriver(CreditDriver dto);
}