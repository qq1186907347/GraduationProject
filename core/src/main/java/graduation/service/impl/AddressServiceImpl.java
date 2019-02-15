package graduation.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.Address;
import graduation.service.IAddressService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl extends BaseServiceImpl<Address> implements IAddressService{

    @Autowired
    AddressMapper addressMapper;
    @Override
    public boolean saveAddress(Address dto) {

        return addressMapper.saveAddress(dto);
    }

    @Override
    public List<Address> selectAddress(IRequest requestContext, Address dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return addressMapper.selectAddress(dto);
    }
}