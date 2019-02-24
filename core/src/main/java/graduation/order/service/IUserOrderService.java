package graduation.order.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.order.dto.UserOrder;

import java.util.List;

public interface IUserOrderService extends IBaseService<UserOrder>, ProxySelf<IUserOrderService>{

    void addOrder(UserOrder dto);

    List<UserOrder> selectByUserId(IRequest requestContext, UserOrder dto, int page, int pageSize);

    void updateOrder(UserOrder dto);
}