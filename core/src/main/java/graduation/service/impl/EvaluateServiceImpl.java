package graduation.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import graduation.mapper.EvaluateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import graduation.dto.Evaluate;
import graduation.service.IEvaluateService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class EvaluateServiceImpl extends BaseServiceImpl<Evaluate> implements IEvaluateService{
    @Autowired
    EvaluateMapper evaluateMapper;

    @Override
    public void addEvaluate(Evaluate dto) {
        evaluateMapper.addEvaluate(dto);
    }

    @Override
    public List<Evaluate> selectEvaluate(IRequest requestContext, Evaluate dto, int page, int pageSize) {
        //分页
        PageHelper.startPage(page, pageSize);
        return evaluateMapper.selectEvaluate(dto);
    }
}