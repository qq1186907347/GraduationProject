package graduation.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.DriverCar;

import java.util.List;

public interface DriverCarMapper extends Mapper<DriverCar>{

    void addCar(DriverCar driverCar);

    List<DriverCar> selectCars(DriverCar dto);
}