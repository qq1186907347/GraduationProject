package graduation.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.dto.Driver;

public interface IDriverService extends IBaseService<Driver>, ProxySelf<IDriverService>{

    void driverRegister(Driver dto);

    Driver selectByDriver(Driver driver);

    Driver checkLogin(Driver dto);
}