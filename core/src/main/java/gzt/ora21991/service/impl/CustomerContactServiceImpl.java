package gzt.ora21991.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import gzt.ora21991.mapper.CustomerContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gzt.ora21991.dto.CustomerContact;
import gzt.ora21991.service.ICustomerContactService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerContactServiceImpl extends BaseServiceImpl<CustomerContact> implements ICustomerContactService{

    @Autowired
    CustomerContactMapper customerContactMapper;
    @Override
    public List<CustomerContact> selectByCustomerId(IRequest requestContext, CustomerContact dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return customerContactMapper.selectByCustomerId(dto);
    }

    @Override
    public void deleteByCustomerId(Long customerId) {
        customerContactMapper.deleteByCustomerId(customerId);
    }
}