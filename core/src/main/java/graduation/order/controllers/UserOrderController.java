package graduation.order.controllers;

import graduation.credit.dto.CreditDriver;
import graduation.credit.dto.CreditUser;
import graduation.credit.service.ICreditDriverService;
import graduation.credit.service.ICreditUserService;
import graduation.money.dto.BalanceDriver;
import graduation.money.dto.BalanceUser;
import graduation.money.service.IBalanceDriverService;
import graduation.money.service.IBalanceUserService;
import graduation.service.IDriverCarService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.order.dto.UserOrder;
import graduation.order.service.IUserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class UserOrderController extends BaseController {

    @Autowired
    private IUserOrderService service;
    @Autowired
    private IBalanceUserService balanceUserService;
    @Autowired
    private ICreditUserService creditUserService;
    @Autowired
    private ICreditDriverService creditDriverService;
    @Autowired
    private IBalanceDriverService balanceDriverService;


    @RequestMapping(value = "/user/order/query")
    @ResponseBody
    public ResponseData query(UserOrder dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        //赋值默认订单
        dto.setOrderStatus(0L);
        return new ResponseData(service.selectByUserId(requestContext, dto, page, pageSize));
    }
    @RequestMapping(value = "/user/order/queryByOrder/{orderId}")
    @ResponseBody
    public ResponseData queryByOrder(UserOrder dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                                     @PathVariable Long orderId) {
        IRequest requestContext = createRequestContext(request);
        dto.setOrderId(orderId);
        return new ResponseData(service.selectByUserId(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/user/order/query/{orderStatus}")
    @ResponseBody
    public ResponseData queryOrder(UserOrder dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request, @PathVariable Long orderStatus) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        //赋值订单状态,0初始状态，1已经完成，2拒绝付款，
        dto.setOrderStatus(orderStatus);
        return new ResponseData(service.selectByUserId(requestContext, dto, page, pageSize));
    }
    @RequestMapping(value = "/driver/order/query/{orderStatus}")
    @ResponseBody
    public ResponseData queryOrderForDriver(UserOrder dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request, @PathVariable Long orderStatus) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        ResponseData responseData = new ResponseData();
        //得到用户id
        Long driverId = (Long) session.getAttribute("driverId");
        dto.setDriverId(driverId);
        //赋值订单状态,0初始状态，1已经完成，2拒绝付款，
        dto.setOrderStatus(orderStatus);
        return new ResponseData(service.selectByUserId(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/user/order/setFinish")
    @ResponseBody
    public ResponseData orderFinish(@RequestBody UserOrder dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            //把订单设置为已经完成的
            dto.setOrderStatus(1L);
            service.updateOrder(dto);


            //用户加积分
            CreditUser creditUser=new CreditUser();
            UserOrder userOrder=service.selectByPrimaryKey(requestContext,dto);
            creditUser.setUserId(userOrder.getUserId());
            creditUser.setOrderId(userOrder.getOrderId());
            creditUser.setCreditType(0L);
            if(Float.valueOf(userOrder.getOrderPrice())>1000.0){
                creditUser.setNum(2L);
            }else {
                creditUser.setNum(1L);
            }
            creditUserService.addCredit(creditUser);

            //给司机加钱
            BalanceDriver balanceDriver=new BalanceDriver();
            balanceDriver.setType(0L);
            balanceDriver.setMoney(Float.valueOf(userOrder.getOrderPrice())*0.9f);
            balanceDriver.setOrderId(String.valueOf(userOrder.getOrderId()));
            balanceDriver.setDriverId(userOrder.getDriverId());
            balanceDriverService.insertSelective(requestContext,balanceDriver);

            //司机加积分
            CreditDriver creditDriver=new CreditDriver();
            creditDriver.setDriverId(userOrder.getDriverId());
            creditDriver.setOrderId(userOrder.getOrderId());
            creditDriver.setCreditType(0L);
            if(Float.valueOf(userOrder.getOrderPrice())>1000.0){
                creditDriver.setNum(2L);
            }else {
                creditDriver.setNum(1L);
            }
            creditDriverService.addCredit(creditDriver);
            return responseData;
        } catch (Exception e) {
            throw e;
/*            responseData.setSuccess(false);
            responseData.setMessage("操作失败,请联系管理员");
            return responseData;*/

        }
    }

    @RequestMapping(value = "/user/order/setRefuse")
    @ResponseBody
    public ResponseData orderRefuse(@RequestBody UserOrder dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            //把订单设置为拒绝状态
            dto.setOrderStatus(2L);
            service.updateOrder(dto);
            return responseData;
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setMessage("操作失败,请联系管理员");
            return responseData;

        }
    }

    @RequestMapping(value = "/user/order/add/{startAddressId}")
    @ResponseBody
    public ResponseData addOrder(@RequestBody UserOrder dto, HttpServletRequest request, @PathVariable Long startAddressId) throws Exception {
        IRequest requestContext = createRequestContext(request);
        try {
            HttpSession session = request.getSession();
            //得到用户id
            Long userId = (Long) session.getAttribute("userId");
            dto.setUserId(userId);
            dto.setOrderStatus(0L);
            //获取用户余额
            BalanceUser user = new BalanceUser();
            user.setUserId(userId);
            Float balance = balanceUserService.getUserBalance(user);
            if (balance > Float.valueOf(dto.getOrderPrice())) {
                service.addOrder(requestContext,dto, startAddressId);
                BalanceUser balanceUser = new BalanceUser();
                balanceUser.setUserId(userId);
                balanceUser.setType(1L);
                balanceUser.setMoney(Float.valueOf(dto.getOrderPrice()));
                balanceUser.setOrderId(String.valueOf(dto.getOrderId()));
                balanceUserService.insertSelective(requestContext, balanceUser);
                ResponseData responseData = new ResponseData(true);
                responseData.setMessage(String.valueOf(dto.getOrderId()));
                return responseData;
            } else {
                ResponseData responseData = new ResponseData(false);
                responseData.setMessage("余额不足,请充值！");
                return responseData;
            }
        } catch (Exception e) {
            e.getMessage();
            if (e.getMessage().equals("Index: 0, Size: 0")) {
                ResponseData responseData = new ResponseData();
                responseData.setSuccess(false);
                responseData.setMessage("无符合车辆的司机，请核对卡车类型和货物总质量，或者请稍后再试");
                return responseData;
            } else {
                e.printStackTrace();
                throw e;
            }
        }
    }

    @RequestMapping(value = "/user/order/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<UserOrder> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/user/order/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<UserOrder> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}