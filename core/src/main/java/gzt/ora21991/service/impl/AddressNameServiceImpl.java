package gzt.ora21991.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import gzt.ora21991.dto.AddressName;
import gzt.ora21991.service.IAddressNameService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AddressNameServiceImpl extends BaseServiceImpl<AddressName> implements IAddressNameService{

}