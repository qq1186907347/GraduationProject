package graduation.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.Evaluate;
import graduation.service.IEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class EvaluateController extends BaseController {

    @Autowired
    private IEvaluateService service;


    @RequestMapping(value = "/evaluate/queryByUser/{evaluateType}")
    @ResponseBody
    public ResponseData query(Evaluate dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long evaluateType) {
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        dto.setEvaluateType(evaluateType);
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectEvaluate(requestContext, dto, page, pageSize));
    }
    @RequestMapping(value = "/evaluate/queryByDriver/{evaluateType}")
    @ResponseBody
    public ResponseData queryByDriver(Evaluate dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long evaluateType) {
        HttpSession session = request.getSession();
        //得到司机id
        Long driverId = (Long) session.getAttribute("driverId");
        dto.setDriverId(driverId);
        dto.setEvaluateType(evaluateType);
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectEvaluate(requestContext, dto, page, pageSize));
    }
    @RequestMapping(value = "/evaluate/query/{orderId}/{evaluateType}")
    @ResponseBody
    public ResponseData queryByOrderId(Evaluate dto, HttpServletRequest request,@PathVariable String orderId,@PathVariable Long evaluateType) {
        IRequest requestContext = createRequestContext(request);
        dto.setEvaluateType(evaluateType);
        dto.setOrderId(orderId);
        return new ResponseData(service.selectEvaluate(requestContext, dto, 1, 1));
    }

    @RequestMapping(value = "/evaluate/add")
    @ResponseBody
    public ResponseData addEvaluate(@RequestBody Evaluate dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData=new ResponseData();
        try {
            service.addEvaluate(dto);
            return responseData;
        }catch (Exception e){
            throw e;
        }
    }

    /**查询订单是否已经评价了*/
    @RequestMapping(value = "/evaluate/isFinish")
    @ResponseBody
    public ResponseData isFinishEvaluate(@RequestBody Evaluate dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData=new ResponseData(false);
        try {
           List<Evaluate> evaluates=service.selectEvaluate(requestContext, dto, 1, 10);
           if(evaluates.size()==0){
               responseData.setSuccess(true);
           }
        }catch (Exception e){
            responseData.setSuccess(false);
        }
        return responseData;
    }


    @RequestMapping(value = "/evaluate/submit")
    @ResponseBody

    public ResponseData update(@RequestBody List<Evaluate> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/evaluate/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Evaluate> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}