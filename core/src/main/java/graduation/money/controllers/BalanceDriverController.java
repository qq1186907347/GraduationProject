package graduation.money.controllers;

import graduation.money.dto.BalanceUser;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.money.dto.BalanceDriver;
import graduation.money.service.IBalanceDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class BalanceDriverController extends BaseController{

    @Autowired
    private IBalanceDriverService service;


    @RequestMapping(value = "/balance/driver/query")
    @ResponseBody
    public ResponseData query(BalanceDriver dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到司机id
        Long driverId = (Long) session.getAttribute("driverId");
        dto.setDriverId(driverId);
        return new ResponseData(service.selectDriverBalance(requestContext,dto,page,pageSize));
    }

        @RequestMapping(value = "/balance/driver/getBalance")
        @ResponseBody
        public ResponseData getBalance(BalanceUser dto, HttpServletRequest request) {
            HttpSession session = request.getSession();
            //得到用户id
            Long driverId = (Long) session.getAttribute("driverId");
            BalanceDriver balanceDriver = new BalanceDriver();
            balanceDriver.setDriverId(driverId);
            Float sum = service.getDriverBalance(balanceDriver);
            ResponseData responseData = new ResponseData();
            responseData.setMessage(String.valueOf(sum));
            return responseData;
        }

    @RequestMapping(value = "/balance/driver/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BalanceDriver> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/balance/driver/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BalanceDriver> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }