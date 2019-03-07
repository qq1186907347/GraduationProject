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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl extends BaseServiceImpl<Address> implements IAddressService{

    @Autowired
    AddressMapper addressMapper;
    /*** 定义锁对象*/
    private Lock lock = new ReentrantLock();
    @Override
    public boolean saveAddress(Address dto) {
        try{
            lock.lock();
            return addressMapper.saveAddress(dto);
        }catch (Exception e){
            throw  e;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public List<Address> selectAddress(IRequest requestContext, Address dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return addressMapper.selectAddress(dto);
    }
}