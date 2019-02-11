package gzt.ora21991.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import gzt.ora21991.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gzt.ora21991.dto.Product;
import gzt.ora21991.service.IProductService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService{
    @Autowired
    ProductMapper productMapper;
    @Override
    public List<Product> selectByCustomerId(IRequest requestContext, Product dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return productMapper.selectByCustomerId(dto);
    }

    @Override
    public void deleteByCustomerId(Long customerId) {
        productMapper.deleteByCustomerId(customerId);
    }
}