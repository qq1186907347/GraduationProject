package graduation.order.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import graduation.order.dto.OGoods;
import graduation.order.service.IOGoodsService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OGoodsServiceImpl extends BaseServiceImpl<OGoods> implements IOGoodsService{

}