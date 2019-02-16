package graduation.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.Goods;

import java.util.List;

public interface GoodsMapper extends Mapper<Goods>{

    void addGoods(Goods goods);

    List<Goods> selectByCollectId(Goods dto);
}