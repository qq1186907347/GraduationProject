package gzt.ora21991.mapper;

import com.hand.hap.mybatis.common.Mapper;
import gzt.ora21991.dto.Customer;
import java.util.List;

public interface CustomerMapper extends Mapper<Customer>{

    List<Customer> selectByCustomerId(Customer dto);

    List<Customer> selectALLCustomer(Customer dto);
}