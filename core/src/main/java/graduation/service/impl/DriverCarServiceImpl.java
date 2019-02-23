package graduation.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.mapper.DriverCarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.DriverCar;
import graduation.service.IDriverCarService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DriverCarServiceImpl extends BaseServiceImpl<DriverCar> implements IDriverCarService{
    @Autowired
    DriverCarMapper driverCarMapper;

    @Override
    public List<DriverCar> selectCars(DriverCar dto) {
        return driverCarMapper.selectCars(dto);
    }
}