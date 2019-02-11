package gzt.ora21991.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import gzt.ora21991.dto.CustomerContact;

import java.util.List;

public interface ICustomerContactService extends IBaseService<CustomerContact>, ProxySelf<ICustomerContactService>{

    List<CustomerContact> selectByCustomerId(IRequest requestContext, CustomerContact dto, int page, int pageSize);

    void deleteByCustomerId(Long customerId);
}