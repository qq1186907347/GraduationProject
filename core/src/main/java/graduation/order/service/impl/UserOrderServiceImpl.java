package graduation.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mail.ReceiverTypeEnum;
import com.hand.hap.mail.dto.MessageReceiver;
import com.hand.hap.mail.service.IMessageService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserOrderServiceImpl extends BaseServiceImpl<UserOrder> implements IUserOrderService {
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
    @Autowired
    IMessageService iMessageService;
    /*** 定义锁对象*/
    private Lock lock = new ReentrantLock();


    private static final Logger logger = LoggerFactory.getLogger(UserOrder.class);

    @Override
    public List<UserOrder> selectByUserId(IRequest requestContext, UserOrder dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return userOrderMapper.selectByUserId(dto);
    }

    @Override
    public void updateOrder(UserOrder dto) {
        try{
            lock.lock();
            userOrderMapper.updateOrder(dto);
        }catch (Exception e){
            throw  e;
        }finally {
            lock.unlock();
        }

    }

    @Override
    public void addOrder(IRequest requestContext, UserOrder dto, Long startAddressId) throws Exception {
        try {
            lock.lock();
            if (dto.getOrderRemake() == null) {
                dto.setOrderRemake("");
            }
            //优先处理需要返程的司机
            Long driverId = getRuningriver(dto, startAddressId);
            if (driverId == -101L) {
                driverId = getEligibleDriver(dto, startAddressId);
            }

            dto.setDriverId(driverId);
            //发送邮件通知
            List<MessageReceiver> recipients = new ArrayList<>();
            // 收件人
            DriverMessage messageDto = new DriverMessage();
            messageDto.setDriverId(driverId);
            List<DriverMessage> driverMessages = driverMessageMapper.selectMessage(messageDto);

            Driver driverDto = new Driver();
            driverDto.setDriverId(dto.getDriverId());
            Driver driver = driverMapper.selectByDriver(driverDto);

            MessageReceiver messageReceiver = new MessageReceiver();
            messageReceiver.setMessageAddress(driver.getEmail());
            messageReceiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
            recipients.add(messageReceiver);
            HashMap<String, Object> templateData = new HashMap<String, Object>();
            templateData.put("realName", driverMessages.get(0).getContactName());
            templateData.put("address", dto.getStartAddress());
            templateData.put("concactName", dto.getContactName());
            templateData.put("phone", dto.getContactPhone());
            //发送邮件
            iMessageService.sendMessage(requestContext, "G_DRIVER", templateData, recipients, (List<Long>) null);
            userOrderMapper.insertSelective(dto);
        } catch (Exception e) {
            logger.debug("增加订单出错！" + e.getMessage());
            e.printStackTrace();
            throw e;
        }finally {
            lock.unlock();
        }

    }

    OCarType getOCarType(CarType carType) {
        OCarType oCarType = null;
        oCarType.setCarId(carType.getCarId());
        oCarType.setAddPrice(carType.getAddPrice());
        oCarType.setCarHeight(carType.getCarHeight());
        oCarType.setCarLong(carType.getCarLong());
        oCarType.setCarType(carType.getCarType());
        return oCarType;
    }

    /**
     * 获取没有在跑订单的司机
     */
    Long getEligibleDriver(UserOrder dto, Long startAddressId) {
        Float sumQuality = getSumQuality(dto);;
        //获取没有在跑订单的司机且符合吨位质量的车辆
        DriverCar driverCar = new DriverCar();
        //获取卡车类型
        CarType carType = carTypeMapper.selectByPrimaryKey(dto.getCarId());
        driverCar.setCarType(carType.getCarType());
        //1000KG=1吨
        driverCar.setCarTonnage(sumQuality / 1000);
        List<DriverCar> driverCars = driverCarMapper.selectCars(driverCar);
        //没有符合的车辆
        List<DriverCar> driverCars2 = new ArrayList<>();
        //查找到符合吨位的车辆后查找符合条件的司机
        for (DriverCar driverCar1 : driverCars) {
            DriverMessage driverMessage = new DriverMessage();
            DriverMessage messageDto = new DriverMessage();
            messageDto.setDriverId(driverCar.getDriverId());
            //获取司机资料
            messageDto.setDriverId(driverCar1.getDriverId());
            driverMessage = driverMessageMapper.selectMessage(messageDto).get(0);
            //获取起点的省市区信息
            Address address = new Address();
            address.setAddressId(startAddressId);
            address = addressMapper.selectAddress(address).get(0);
            //司机实名认证的地址是否和发货地址一致
            if (address.getTown().equals(driverMessage.getTown())) {
                return driverMessage.getDriverId();
            }
            if (address.getCity().equals(driverMessage.getCity())) {
                return driverMessage.getDriverId();
            }
        }
        return driverCars2.get(0).getDriverId();
    }

    /**
     * 获取在跑订单的司机,避免司机回程拉空车
     */
    Long getRuningriver(UserOrder dto, Long startAddressId) {
        Float sumQuality = getSumQuality(dto);
        DriverMessage driverMessageDto = new DriverMessage();
        UserOrder uDto=new UserOrder();

        //获取在跑订单的司机且符合吨位质量的车辆
        DriverCar driverCar = new DriverCar();
        //获取卡车类型
        CarType carType = carTypeMapper.selectByPrimaryKey(dto.getCarId());
        driverCar.setCarType(carType.getCarType());
        //1000KG=1吨
        driverCar.setCarTonnage(sumQuality / 1000);
        List<DriverCar> driverCars = driverCarMapper.selectRCars(driverCar);

        //获取起点的省市区信息
        Address address = new Address();
        address.setAddressId(startAddressId);
        address = addressMapper.selectAddress(address).get(0);
        //起点的省市
        String city = address.getProvince() + address.getCity();
        //起点的省市区
        String town = address.getProvince() + address.getCity() + address.getTown();

        //设置DTO作为查询参数，
        UserOrder runingOrder = new UserOrder();
        runingOrder.setEndAddress(town);
        //得到结果集，在跑的订单
        List<UserOrder> runingOrders = userOrderMapper.selectROrder(runingOrder);
        //遍历结果集找到正在跑的订单的'终点'是现在准备下的订单的'起点'
        for (UserOrder userOrder : runingOrders) {
            for(DriverCar carTem:driverCars){
                //获取符合条件的车辆的所属司机信息
                DriverMessage mDto=new DriverMessage();
                mDto.setDriverId(carTem.getDriverId());
                DriverMessage driverMessage=driverMessageMapper.selectMessage(mDto).get(0);
                String mTown=driverMessage.getProvince()+driverMessage.getCity()+driverMessage.getTown();
                String mCity=driverMessage.getProvince()+driverMessage.getCity();
                //判断正在跑的订单的起始地是不是司机归属地
                if(userOrder.getStartAddress().contains(mTown)){
                    uDto.setDriverId(userOrder.getDriverId());
                    List<UserOrder> userOrderList=userOrderMapper.selectROrder(uDto);
                    //司机没有回程的数据
                    if(userOrderList.size()<2){
                        return userOrder.getDriverId();
                    }
                }else if(userOrder.getStartAddress().contains(mCity)){
                    uDto.setDriverId(userOrder.getDriverId());
                    List<UserOrder> userOrderList=userOrderMapper.selectROrder(uDto);
                    //司机没有回程的数据
                    if(userOrderList.size()<2){
                        return userOrder.getDriverId();
                    }
                }
            }
        }


        return -101L;
    }

    /**获取货物总质量**/
    Float getSumQuality(UserOrder dto){
        Float sumQuality = 0f;
        DriverMessage driverMessageDto = new DriverMessage();
        Goods goods = new Goods();
        goods.setCollectId(dto.getCollectId());
        List<Goods> goodsList = goodsMapper.selectByCollectId(goods);
        //计算该订单货物总质量
        for (Goods bean : goodsList) {
            sumQuality = sumQuality + Float.valueOf(bean.getGoodsQuality()) * bean.getGoodsNum();
        }
        return  sumQuality;

    }
}