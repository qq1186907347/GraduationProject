package graduation.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.CarRepair;

import java.util.List;

public interface CarRepairMapper extends Mapper<CarRepair>{

    List<CarRepair> selectCarRepair(CarRepair dto);
}