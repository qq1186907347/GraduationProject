package gzt.ora21991.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import gzt.ora21991.dto.Product;

import java.util.List;

public interface IProductService extends IBaseService<Product>, ProxySelf<IProductService>{

    List<Product> selectByCustomerId(IRequest requestContext, Product dto, int page, int pageSize);

    void deleteByCustomerId(Long customerId);
}