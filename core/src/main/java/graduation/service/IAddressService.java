package graduation.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.dto.Address;

import java.util.List;

public interface IAddressService extends IBaseService<Address>, ProxySelf<IAddressService>{

    boolean saveAddress(Address dto);

    List<Address> selectAddress(IRequest requestContext, Address dto, int page, int pageSize);
}