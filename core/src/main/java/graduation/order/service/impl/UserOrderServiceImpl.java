package graduation.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.dto.*;
import graduation.mapper.*;
import graduation.order.dto.OCarType;
import graduation.order.mapper.OCarTypeMapper;
import graduation.order.mapper.OGoodsCollectMapper;
import graduation.order.mapper.OGoodsMapper;
import graduation.order.mapper.UserOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.order.dto.UserOrder;
import graduation.order.service.IUserOrderService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserOrderServiceImpl extends BaseServiceImpl<UserOrder> implements IUserOrderService{
    @Autowired
    CarTypeMapper carTypeMapper;
    @Autowired
    UserOrderMapper userOrderMapper;
    @Autowired
    OCarTypeMapper oCarTypeMapper;
    @Autowired
    OGoodsMapper oGoodsMapper;
    @Autowired
    OGoodsCollectMapper oGoodsCollectMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsCollectMapper goodsCollectMapper;
    @Autowired
    DriverCarMapper driverCarMapper;
    @Autowired
    DriverMapper driverMapper;
    @Autowired
    DriverMessageMapper driverMessageMapper;
    @Autowired
    AddressMapper addressMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserOrder.class);

    @Override
    public List<UserOrder> selectByUserId(IRequest requestContext, UserOrder dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return userOrderMapper.selectByUserId(dto);
    }

    @Override
    public void updateOrder(UserOrder dto) {
        userOrderMapper.updateOrder(dto);
    }

    @Override
    public void addOrder(UserOrder dto, Long startAddressId) {
        try {
            if(dto.getOrderRemake()==null){
                dto.setOrderRemake("");
            }
            Long driverId=getEligibleDriver(dto,startAddressId);
            dto.setDriverId(driverId);
            userOrderMapper.addOrder(dto);
        }catch (Exception e){
            logger.debug("增加订单出错！" + e.getMessage());
            e.printStackTrace();
            throw  e;
        }

    }

    OCarType getOCarType(CarType carType){
        OCarType oCarType=null;
        oCarType.setCarId(carType.getCarId());
        oCarType.setAddPrice(carType.getAddPrice());
        oCarType.setCarHeight(carType.getCarHeight());
        oCarType.setCarLong(carType.getCarLong());
        oCarType.setCarType(carType.getCarType());
        return  oCarType;
    }
    /**获取没有在跑订单的司机*/
    Long getEligibleDriver(UserOrder dto, Long startAddressId){
        Goods goods=new Goods();
        goods.setCollectId(dto.getCollectId());
        List<Goods> goodsList=goodsMapper.selectByCollectId(goods);
        float sumQuality=0;
        //计算该订单货物总质量
        for(Goods bean:goodsList){
            sumQuality=sumQuality+Float.valueOf(bean.getGoodsQuality())*bean.getGoodsNum();
        }
        //获取没有在跑订单的司机且符合吨位质量的车辆
        DriverCar driverCar=new DriverCar();
        //获取卡车类型
        CarType carType=carTypeMapper.selectByPrimaryKey(dto.getCarId());
        driverCar.setCarType(carType.getCarType());
        driverCar.setCarTonnage(sumQuality);
        List<DriverCar> driverCars=driverCarMapper.selectCars(driverCar);
        //查找到符合吨位的车辆后查找符合条件的司机
        for(DriverCar driverCar1:driverCars){
            DriverMessage driverMessage=new DriverMessage();
            DriverMessage messageDto=new DriverMessage();
            messageDto.setDriverId(driverCar.getDriverId());
            messageDto.setMessageStatus(1L);
            driverMessage=driverMessageMapper.selectMessage(messageDto);
            Address address=new Address();
            address.setAddressId(startAddressId);
            address=addressMapper.selectByPrimaryKey(address);
            //司机实名认证的地址是否和发货地址一致
            if(address.getTown().equals(driverMessage.getTown())){
                return  driverMessage.getDriverId();
            }
        }
        return -1L;
    }
}