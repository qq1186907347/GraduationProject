package graduation.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.dto.Goods;

import java.util.List;

public interface IGoodsService extends IBaseService<Goods>, ProxySelf<IGoodsService>{

    List<Goods> selectByCollectId(IRequest requestContext, Goods dto, int page, int pageSize);
}