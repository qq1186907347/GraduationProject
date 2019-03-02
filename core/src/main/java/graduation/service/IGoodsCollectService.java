package graduation.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.dto.GoodsCollect;

import java.util.List;

public interface IGoodsCollectService extends IBaseService<GoodsCollect>, ProxySelf<IGoodsCollectService>{

    boolean addCollect(GoodsCollect dto);

    List<GoodsCollect> selectCollect(GoodsCollect dto);

    void setCollectStatus(List<GoodsCollect> dto);
}