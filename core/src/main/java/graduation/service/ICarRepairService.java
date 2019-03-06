package graduation.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.dto.CarRepair;

import java.util.List;

public interface ICarRepairService extends IBaseService<CarRepair>, ProxySelf<ICarRepairService>{

    List<CarRepair> selectCarRepair(IRequest requestContext, CarRepair dto, int page, int pageSize);
}