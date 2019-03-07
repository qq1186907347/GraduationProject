package graduation.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.Driver;
import graduation.service.IDriverService;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional(rollbackFor = Exception.class)
public class DriverServiceImpl extends BaseServiceImpl<Driver> implements IDriverService{
    @Autowired
    DriverMapper driverMapper;
    /*** 定义锁对象*/
    private Lock lock = new ReentrantLock();

    @Override
    public void driverRegister(Driver dto) {
        try{
            lock.lock();
            driverMapper.driverRegister(dto);

        }catch (Exception e){
            throw e;
        }finally {
            lock.unlock();
        }
        
    }

    @Override
    public Driver selectByDriver(Driver driver) {
        return driverMapper.selectByDriver(driver);
    }

    @Override
    public Driver checkLogin(Driver dto) {
        return driverMapper.checkLogin(dto);
    }
}