package graduation.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.GoodsCollect;

import java.util.List;

public interface GoodsCollectMapper extends Mapper<GoodsCollect>{

    void addCollect(GoodsCollect dto);

    List<GoodsCollect> selectCollect(GoodsCollect dto);

    void setCollectStatus(GoodsCollect collect);
}