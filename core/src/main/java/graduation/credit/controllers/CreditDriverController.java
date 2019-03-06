package graduation.credit.controllers;

import graduation.credit.dto.CreditUser;
import graduation.money.dto.BalanceDriver;
import graduation.money.dto.BalanceUser;
import graduation.money.service.IBalanceDriverService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.credit.dto.CreditDriver;
import graduation.credit.service.ICreditDriverService;
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
public class CreditDriverController extends BaseController {

    @Autowired
    private ICreditDriverService service;
    @Autowired
    private IBalanceDriverService balanceDriverService;


    @RequestMapping(value = "/credit/driver/query")
    @ResponseBody
    public ResponseData query(CreditDriver dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到司机id
        Long driverId = (Long) session.getAttribute("driverId");
        dto.setDriverId(driverId);
        return new ResponseData(service.selectCreditDriver(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/credit/driver/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CreditDriver> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/credit/driver/getCredit")
    @ResponseBody
    public ResponseData getCredit(@RequestBody CreditDriver dto,HttpServletRequest request) {
        ResponseData responseData=new ResponseData();
        Float credit=Float.valueOf(0);
        try {
            IRequest requestContext = createRequestContext(request);
            HttpSession session = request.getSession();
            //得到司机id
            Long driverId = (Long) session.getAttribute("driverId");
            //获取司机积分
            CreditDriver creditDriver = new CreditDriver();
            creditDriver.setDriverId(driverId);
            credit= service.getDriverCredit(creditDriver);
            if(dto.getNum()==null){
                dto.setNum(0L);
            }
            if(credit<Float.valueOf(dto.getNum())){
                responseData.setSuccess(false);
                responseData.setMessage(String.valueOf(credit));
            }
        }catch (Exception e){
            throw  e;
        }
        responseData.setMessage(String.valueOf(credit));
        return responseData;
    }

    @RequestMapping(value = "/credit/driver/creditExchange")
    @ResponseBody
    public ResponseData creditExchange(@RequestBody CreditDriver dto,HttpServletRequest request) {
        ResponseData responseData=new ResponseData();
        Float credit=Float.valueOf(0);
        try {

            IRequest requestContext = createRequestContext(request);
            HttpSession session = request.getSession();
            //得到司机id
            Long driverId = (Long) session.getAttribute("driverId");
            //获取司机积分
            CreditDriver creditDriver = new CreditDriver();
            creditDriver.setDriverId(driverId);
            credit= service.getDriverCredit(creditDriver);
            if(dto.getNum()==null){
                dto.setNum(0L);
            }
            if(credit<Float.valueOf(dto.getNum())){
                responseData.setSuccess(false);
                responseData.setMessage(String.valueOf(credit));
            }else{
                //相应数额兑换相应钱
                BalanceDriver balanceDriver=new BalanceDriver();
                balanceDriver.setDriverId(driverId);
                balanceDriver.setOrderId("-1");
                balanceDriver.setType(1L);
                if (dto.getNum()==1L){
                    balanceDriver.setMoney(1f);
                    balanceDriverService.insertSelective(requestContext,balanceDriver);
                }
                if (dto.getNum()==10L){
                    balanceDriver.setMoney(50f);
                    balanceDriverService.insertSelective(requestContext,balanceDriver);
                }
                if (dto.getNum()==20L){
                    balanceDriver.setMoney(100f);
                    balanceDriverService.insertSelective(requestContext,balanceDriver);
                }
                if (dto.getNum()==50L){
                    balanceDriver.setMoney(500f);
                    balanceDriverService.insertSelective(requestContext,balanceDriver);
                }
                //积分减少
                CreditDriver creditDriver1=new CreditDriver();
                creditDriver1.setNum(dto.getNum());
                creditDriver1.setDriverId(driverId);
                creditDriver1.setCreditType(1L);
                creditDriver1.setOrderId(-1L);
                service.addCredit(creditDriver1);
            }
        }catch (Exception e){
            throw  e;
        }
        responseData.setMessage(String.valueOf(credit));
        return responseData;
    }

    @RequestMapping(value = "/credit/driver/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CreditDriver> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}