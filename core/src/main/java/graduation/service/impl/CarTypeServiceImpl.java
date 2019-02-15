package graduation.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import graduation.dto.CarType;
import graduation.service.ICarTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CarTypeServiceImpl extends BaseServiceImpl<CarType> implements ICarTypeService{

}