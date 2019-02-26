package graduation.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.dto.UserComplain;

import java.util.List;

public interface IUserComplainService extends IBaseService<UserComplain>, ProxySelf<IUserComplainService>{

    List<UserComplain> selectUserComplain(UserComplain dto);

    void addComplain(UserComplain dto);

    List<UserComplain> selectUserComplain(IRequest requestContext, UserComplain dto, int page, int pageSize);
}