package graduation.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.mapper.CarRepairMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.CarRepair;
import graduation.service.ICarRepairService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CarRepairServiceImpl extends BaseServiceImpl<CarRepair> implements ICarRepairService {
    @Autowired
    CarRepairMapper carRepairMapper;

    @Override
    public List<CarRepair> selectCarRepair(IRequest requestContext, CarRepair dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return carRepairMapper.selectCarRepair(dto);
    }
}