package graduation.user.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.user.dto.User2;

public interface IUser2Service extends IBaseService<User2>, ProxySelf<IUser2Service>{

    User2 selectByUserName(User2 dto);

    int register(User2 dto);

    User2 checkLogin(User2 dto);
}