package graduation.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.User2;

import java.util.List;

public interface User2Mapper extends Mapper<User2>{

    User2 selectByUserName(User2 dto);

    int register(User2 dto);

    User2 checkLogin(User2 dto);

    List<User2> selectUser(User2 dto);
}