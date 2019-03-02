package graduation.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.dto.DriverMessage;
import graduation.mapper.DriverMessageMapper;
import graduation.mapper.RefuseResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.RefuseResult;
import graduation.service.IRefuseResultService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional(rollbackFor = Exception.class)
public class RefuseResultServiceImpl extends BaseServiceImpl<RefuseResult> implements IRefuseResultService{
    @Autowired
    RefuseResultMapper refuseResultMapper;
    @Autowired
    DriverMessageMapper driverMessageMapper;
    /*** 定义锁对象*/
    private Lock lock = new ReentrantLock();

    @Override
    public void addResult(RefuseResult dto) {
        try {
            lock.lock();
            refuseResultMapper.addResult(dto);
            DriverMessage driverMessage=new  DriverMessage();
            driverMessage.setMessageStatus(2L);
            driverMessage.setMessageId(dto.getMessageId());
            driverMessageMapper.setMessageStatus(driverMessage);
        }catch (Exception e){
            throw  e;
        }finally {
                lock.unlock();
        }


    }

    @Override
    public List<RefuseResult> selectRefuse(RefuseResult dto) {
        return refuseResultMapper.selectRefuse(dto);
    }
}