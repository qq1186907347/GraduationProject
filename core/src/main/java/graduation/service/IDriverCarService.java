package graduation.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.dto.DriverCar;

import java.util.List;

public interface IDriverCarService extends IBaseService<DriverCar>, ProxySelf<IDriverCarService>{

    List<DriverCar> selectCars(DriverCar dto);
}