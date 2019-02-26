package graduation.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.UserComplain;

import java.util.List;

public interface UserComplainMapper extends Mapper<UserComplain>{

    List<UserComplain> selectUserComplain(UserComplain dto);

    void addComplain(UserComplain dto);
}