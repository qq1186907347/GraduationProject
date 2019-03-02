package graduation.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.dto.Goods;
import graduation.mapper.GoodsCollectMapper;
import graduation.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.GoodsCollect;
import graduation.service.IGoodsCollectService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsCollectServiceImpl extends BaseServiceImpl<GoodsCollect> implements IGoodsCollectService{

    @Autowired
    GoodsCollectMapper goodsCollectMapper;
    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 定义锁对象
     */
    private Lock lock = new ReentrantLock();
    @Override
    public boolean addCollect(GoodsCollect dto) {
        try {
            goodsCollectMapper.insertSelective(dto);;
            checkGoods(dto);
            return true;
        }catch (Exception e){
            throw  e;
        }
    }

    @Override
    public List<GoodsCollect> selectCollect(GoodsCollect dto) {
        return goodsCollectMapper.selectCollect(dto);
    }

    @Override
    public void setCollectStatus(List<GoodsCollect> dto) {
        try{
            lock.lock();
            for(GoodsCollect collect:dto){
                collect.setCollectStatus(1L);
                goodsCollectMapper.setCollectStatus(collect);
            }
        }catch (Exception e){
            throw  e;
        }finally {
            lock.unlock();

        }

    }

    /**
     * 更新或者保存货物
     */
    @Transactional(rollbackFor = Exception.class)
    void checkGoods(GoodsCollect goodsCollect) {
        if (goodsCollect.getCollectId()!= null) {
            if (goodsCollect.getGoodsList() != null) {
                for (Goods goods : goodsCollect.getGoodsList()) {
                    //add goods
                    if (goods.getGoodsId()==null) {
                        goods.setCollectId(goodsCollect.getCollectId());
                        goodsMapper.addGoods(goods);
                    } else {
                        //update goods
                        goodsMapper.addGoods(goods);
                    }
                }
            }
        }
    }
}