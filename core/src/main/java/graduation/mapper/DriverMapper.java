package graduation.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.Driver;

public interface DriverMapper extends Mapper<Driver>{

    void driverRegister(Driver dto);

    Driver selectByDriver(Driver driver);

    Driver checkLogin(Driver dto);
}