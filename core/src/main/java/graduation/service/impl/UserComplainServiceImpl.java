package graduation.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.mapper.UserComplainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.UserComplain;
import graduation.service.IUserComplainService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserComplainServiceImpl extends BaseServiceImpl<UserComplain> implements IUserComplainService{
    @Autowired
    UserComplainMapper userComplainMapper;

    @Override
    public List<UserComplain> selectUserComplain(UserComplain dto) {
        return userComplainMapper.selectUserComplain(dto);
    }

    @Override
    public void addComplain(UserComplain dto) {
        userComplainMapper.addComplain(dto);
    }

    @Override
    public List<UserComplain> selectUserComplain(IRequest requestContext, UserComplain dto, int page, int pageSize) {
        //设置页数
        PageHelper.startPage(page, pageSize);
        return userComplainMapper.selectUserComplain(dto);
    }
}