package graduation.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.UserComplain;
import graduation.service.IUserComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class UserComplainController extends BaseController {

    @Autowired
    private IUserComplainService service;


    @RequestMapping(value = "/user/complain/queryById/{complainStatus}")
    @ResponseBody
    public ResponseData queryById(UserComplain dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long complainStatus) {
        IRequest requestContext = createRequestContext(request);
        dto.setComplainStatus(complainStatus);
        return new ResponseData(service.selectUserComplain(requestContext, dto, page, pageSize));
    }
    @RequestMapping(value = "/user/complain/queryUser")
    @ResponseBody
    public ResponseData queryUser(UserComplain dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        return new ResponseData(service.selectUserComplain(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/user/complain/queryDriver")
    @ResponseBody
    public ResponseData queryDriver(UserComplain dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到司机id
        Long driverId = (Long) session.getAttribute("driverId");
        dto.setDriverId(driverId);
        return new ResponseData(service.selectUserComplain(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/user/complain/query/{orderId}")
    @ResponseBody
    public ResponseData queryByOrderId(UserComplain dto,HttpServletRequest request,@PathVariable Long orderId) {
        IRequest requestContext = createRequestContext(request);
        dto.setOrderId(orderId);
        return new ResponseData(service.selectUserComplain(dto));
    }

    @RequestMapping(value = "/user/complain/setFinish")
    @ResponseBody
    public ResponseData orderFinish(@RequestBody UserComplain dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            dto=service.selectByPrimaryKey(requestContext,dto);
            //把投诉设置成已经处理
            dto.setComplainStatus(1L);
            service.updateByPrimaryKey(requestContext,dto);
            return responseData;
        } catch (Exception e) {
            throw  e;
 /*           responseData.setSuccess(false);
            responseData.setMessage("操作失败,请联系管理员");
            return responseData;*/

        }
    }

    @RequestMapping(value = "/user/complain/add")
    @ResponseBody
    public ResponseData addComplain(@RequestBody UserComplain dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData=new ResponseData();
        try {
            dto.setComplainStatus(0L);
            service.addComplain(dto);
            return responseData;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 查询订单是否已经投诉了
     */
    @RequestMapping(value = "/user/complain/isFinish")
    @ResponseBody
    public ResponseData isFinishComplain(@RequestBody UserComplain dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData(false);
        try {
            List<UserComplain> evaluates = service.selectUserComplain(dto);
            if (evaluates.size() == 0) {
                responseData.setSuccess(true);
            }
        } catch (Exception e) {
            responseData.setSuccess(false);
        }
        return responseData;
    }

    @RequestMapping(value = "/user/complain/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<UserComplain> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/user/complain/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<UserComplain> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}