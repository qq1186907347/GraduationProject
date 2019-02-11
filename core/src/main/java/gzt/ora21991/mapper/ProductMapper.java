package gzt.ora21991.mapper;

import com.hand.hap.mybatis.common.Mapper;
import gzt.ora21991.dto.Product;

import java.util.List;

public interface ProductMapper extends Mapper<Product>{

    List<Product> selectByCustomerId(Product dto);

    void deleteByCustomerId(Long customerId);
}