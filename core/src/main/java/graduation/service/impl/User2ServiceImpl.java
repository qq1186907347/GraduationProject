package graduation.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.mapper.User2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.User2;
import graduation.service.IUser2Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class User2ServiceImpl extends BaseServiceImpl<User2> implements IUser2Service{
    @Autowired
    User2Mapper user2Mapper;

    @Override
    public User2 selectByUserName(User2 dto) {
        return user2Mapper.selectByUserName(dto);
    }

    @Override
    public int register(User2 dto) {
        return user2Mapper.register(dto);
    }

    @Override
    public User2 checkLogin(User2 dto) {
        return user2Mapper.checkLogin(dto);
    }
}