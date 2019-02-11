package graduation.user.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.user.dto.User2;

public interface User2Mapper extends Mapper<User2>{

    User2 selectByUserName(User2 dto);

    int register(User2 dto);

    User2 checkLogin(User2 dto);
}