package gzt.ora21991.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import gzt.ora21991.dto.Customer;

import java.util.List;

public interface ICustomerService extends IBaseService<Customer>, ProxySelf<ICustomerService>{

    List<Customer> selectByCustomerId(Customer dto);

    List<Customer> update(List<Customer> customers, IRequest requestContext);

    void createCustomer(Customer customer, IRequest iRequest);

    void updateCustomer(Customer customer,IRequest iRequest);

    List<Customer> selectALLCustomer(IRequest requestContext, Customer dto, int page, int pageSize);
}