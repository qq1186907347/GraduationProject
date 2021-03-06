package graduation.order.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import graduation.order.dto.OGoodsCollect;
import graduation.order.service.IOGoodsCollectService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OGoodsCollectServiceImpl extends BaseServiceImpl<OGoodsCollect> implements IOGoodsCollectService{

}