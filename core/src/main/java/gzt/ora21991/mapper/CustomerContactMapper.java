package gzt.ora21991.mapper;

import com.hand.hap.mybatis.common.Mapper;
import gzt.ora21991.dto.CustomerContact;

import java.util.List;

public interface CustomerContactMapper extends Mapper<CustomerContact>{

    List<CustomerContact> selectByCustomerId(CustomerContact dto);

    void deleteByCustomerId(Long customerId);
}