package graduation.credit.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.credit.dto.CreditUser;

import java.util.List;

public interface CreditUserMapper extends Mapper<CreditUser>{

    List<CreditUser> selectCreditUser(CreditUser creditUser);

    void addCredit(CreditUser dto);
}