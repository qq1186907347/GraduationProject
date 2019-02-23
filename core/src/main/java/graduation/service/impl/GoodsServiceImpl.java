package graduation.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.Goods;
import graduation.service.IGoodsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl extends BaseServiceImpl<Goods> implements IGoodsService{
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<Goods> selectByCollectId(IRequest requestContext, Goods dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return goodsMapper.selectByCollectId(dto);
    }
}