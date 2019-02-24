package graduation.order.controllers;

import graduation.service.IDriverCarService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.order.dto.UserOrder;
import graduation.order.service.IUserOrderService;
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
public class UserOrderController extends BaseController {

    @Autowired
    private IUserOrderService service;
    @Autowired
    private IDriverCarService driverCarService;


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
        return new ResponseData(service.selectByUserId(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/user/order/query/finish")
    @ResponseBody
    public ResponseData queryFinish(UserOrder dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        //赋值已完成订单
        dto.setOrderStatus(1L);
        return new ResponseData(service.selectByUserId(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/user/order/setFinish")
    @ResponseBody
    public ResponseData orderFinish(@RequestBody UserOrder dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData=new ResponseData();
        try{
            //把订单设置为已经完成的
            dto.setOrderStatus(1L);
            service.updateOrder(dto);
            return responseData;
        }catch (Exception e){
            responseData.setSuccess(false);
            responseData.setMessage("操作失败,请联系管理员");
            return responseData;

        }
    }

    @RequestMapping(value = "/user/order/add")
    @ResponseBody
    public ResponseData addOrder(@RequestBody UserOrder dto,HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        try {
            HttpSession session = request.getSession();
            //得到用户id
            Long userId = (Long) session.getAttribute("userId");
            dto.setUserId(userId);
            dto.setOrderStatus(0L);
            service.addOrder(dto);
            return new ResponseData(true);
        } catch (Exception e) {
            e.getMessage();
            if(e.getMessage().equals("Index: 0, Size: 0")){
                ResponseData responseData =new ResponseData();
                responseData.setSuccess(false);
                responseData.setMessage("无符合车辆的司机，请核对卡车类型和货物总质量，或者请稍后再试");
                return responseData;
            }else{
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