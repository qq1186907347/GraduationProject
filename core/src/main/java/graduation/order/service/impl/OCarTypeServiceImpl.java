package graduation.order.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import graduation.order.dto.OCarType;
import graduation.order.service.IOCarTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OCarTypeServiceImpl extends BaseServiceImpl<OCarType> implements IOCarTypeService{

}