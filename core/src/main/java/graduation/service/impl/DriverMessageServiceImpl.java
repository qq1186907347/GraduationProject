package graduation.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.dto.DriverCar;
import graduation.mapper.DriverCarMapper;
import graduation.mapper.DriverMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.DriverMessage;
import graduation.service.IDriverMessageService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DriverMessageServiceImpl extends BaseServiceImpl<DriverMessage> implements IDriverMessageService{
    @Autowired
    DriverMessageMapper driverMessageMapper;
    @Autowired
    DriverCarMapper driverCarMapper;

    @Override
    public void addMessage(DriverMessage dto) {
        try{
            if(dto.getCarList()!=null){
                for(DriverCar driverCar:dto.getCarList()){
                    driverCar.setDriverId(dto.getDriverId());
                    driverCarMapper.addCar(driverCar);
                }
            }
            driverMessageMapper.addMessage(dto);
        }catch (Exception e){
            throw  e;
        }

    }

    @Override
    public List<DriverMessage> selectPass(DriverMessage dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        dto.setMessageStatus(1L);
        return driverMessageMapper.selectMessage(dto);
    }

    @Override
    public List<DriverMessage> selectUnPass(DriverMessage dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        dto.setMessageStatus(0L);
        return driverMessageMapper.selectMessage(dto);
    }

    @Override
    public boolean setPass(List<DriverMessage> dto) {
        boolean success=true;
        try{
            for(DriverMessage driverMessage : dto){
                //审核不通过，状态为1
                driverMessage.setMessageStatus(1L);
                driverMessageMapper.setMessageStatus(driverMessage);
            }
            success=true;

        }catch (Exception e){
            success= false;
            throw  e;
        }finally {
            return  success;
        }
    }

    @Override
    public boolean setUnPass(List<DriverMessage> dto) {

        boolean success=true;
        try{
            for(DriverMessage driverMessage : dto){
                //审核不通过，状态为2
                driverMessage.setMessageStatus(2L);
                driverMessageMapper.setMessageStatus(driverMessage);
            }
            success=true;

        }catch (Exception e){
            success= false;
            throw  e;
        }finally {
            return  success;
        }
    }

    @Override
    public List<DriverMessage>  isAuthenticated(DriverMessage dto) {
       return driverMessageMapper.isAuthenticated(dto);
    }

    @Override
    public void updateMessageById(DriverMessage driverMessage) {
        driverMessageMapper.updateMessageById(driverMessage);
    }

    @Override
    public List<DriverMessage> selectMessage(DriverMessage dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return driverMessageMapper.selectMessage(dto);
    }

}