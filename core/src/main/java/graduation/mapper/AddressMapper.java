package graduation.mapper;
import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.Address;

import java.util.List;

public interface AddressMapper extends Mapper<Address>{
    boolean saveAddress(Address dto);

    List<Address> selectAddress(Address dto);
}