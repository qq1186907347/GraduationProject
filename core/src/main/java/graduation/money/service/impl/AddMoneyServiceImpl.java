package graduation.money.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.money.mapper.AddMoneyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.money.dto.AddMoney;
import graduation.money.service.IAddMoneyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AddMoneyServiceImpl extends BaseServiceImpl<AddMoney> implements IAddMoneyService{
    @Autowired
    AddMoneyMapper addMoneyMapper;


    @Override
    public List<AddMoney> selectAddMoney(IRequest requestContext, AddMoney dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return addMoneyMapper.selectAddMoney(dto);
    }
}