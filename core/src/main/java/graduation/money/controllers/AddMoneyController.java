package graduation.money.controllers;

import graduation.money.dto.BalanceUser;
import graduation.money.service.IBalanceUserService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.money.dto.AddMoney;
import graduation.money.service.IAddMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class AddMoneyController extends BaseController {

    @Autowired
    private IAddMoneyService service;
    @Autowired
    private IBalanceUserService balanceUserService;


    @RequestMapping(value = "/add/money/query/{addType}")
    @ResponseBody
    public ResponseData query(AddMoney dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long addType) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        dto.setAddType(addType);
        return new ResponseData(service.selectAddMoney(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/add/money/queryAll/{addType}")
    @ResponseBody
    public ResponseData queryAll(AddMoney dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long addType) {
        IRequest requestContext = createRequestContext(request);
        dto.setAddType(addType);
        return new ResponseData(service.selectAddMoney(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/add/money/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<AddMoney> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/add/money/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<AddMoney> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/add/money/add")
    @ResponseBody
    public ResponseData add(@RequestBody AddMoney dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            HttpSession session = request.getSession();
            //得到用户id
            Long userId = (Long) session.getAttribute("userId");
            dto.setUserId(userId);
            AddMoney dto2 = new AddMoney();
            dto2.setOrderId(dto.getOrderId());
            dto2.setAddType(0L);
            List<AddMoney> addMoneys = service.selectAddMoney(requestContext, dto2, 1, 1);
            dto2.setAddType(1L);
            List<AddMoney> addMoneys2 = service.selectAddMoney(requestContext, dto2, 1, 1);
            if (addMoneys.size() > 0 || addMoneys2.size() > 0) {
                responseData.setSuccess(false);
                responseData.setMessage("该订单号已经充值，请不要重复操作！");
            } else {
                service.insertSelective(requestContext, dto);
            }
            return responseData;
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/add/money/setFinish")
    @ResponseBody
    public ResponseData setFinish(@RequestBody AddMoney dto,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                  HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            AddMoney addMoney = service.selectByPrimaryKey(requestContext, dto);
            addMoney.setAddType(1L);
            service.updateByPrimaryKey(requestContext, addMoney);
            //给流水表赋值
            BalanceUser balanceUser = new BalanceUser();
            balanceUser.setUserId(addMoney.getUserId());
            balanceUser.setMoney(addMoney.getMoney());
            balanceUser.setOrderId(addMoney.getOrderId());
            balanceUser.setType(0L);
            balanceUserService.insertSelective(requestContext, balanceUser);
            //获取未审核数据
            AddMoney addMoney1=new AddMoney();
            addMoney1.setAddType(0L);
            List<AddMoney> addMonies=service.selectAddMoney(requestContext,addMoney1,page,pageSize);
            responseData.setRows(addMonies);
            return responseData;
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/add/money/setRefuse")
    @ResponseBody
    public ResponseData setRefuse(@RequestBody AddMoney dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            AddMoney addMoney = service.selectByPrimaryKey(requestContext, dto);
            addMoney.setAddType(2L);
            service.updateByPrimaryKey(requestContext, addMoney);
            return responseData;
        } catch (Exception e) {
            throw e;
        }
    }

}