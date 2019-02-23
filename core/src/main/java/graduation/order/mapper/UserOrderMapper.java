package graduation.order.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.order.dto.UserOrder;

import java.util.List;

public interface UserOrderMapper extends Mapper<UserOrder>{

    void addOrder(UserOrder dto);

    List<UserOrder> selectByUserId(UserOrder dto);
}