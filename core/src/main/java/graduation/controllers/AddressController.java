package graduation.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.Address;
import graduation.service.IAddressService;
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
public class AddressController extends BaseController {

    @Autowired
    private IAddressService service;


    @RequestMapping(value = "/address/query")
    @ResponseBody
    public ResponseData query(Address dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        return new ResponseData(service.selectAddress(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/address/add")
    @ResponseBody
    public ResponseData saveAddress(@RequestBody  Address dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData=new ResponseData();
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        try{
            if(dto.getProvince().equals("省份")){
                responseData.setSuccess(false);
                responseData.setMessage("省份填写不正确!");
                return  responseData;
            }
            if(dto.getCity().equals("地级市")){
                responseData.setSuccess(false);
                responseData.setMessage("地级市填写不正确!");
                return  responseData;
            }
            if(dto.getTown().equals("市、县级市、县")){
                responseData.setSuccess(false);
                responseData.setMessage("(市、县级市、县)填写不正确!");
                return  responseData;
            }
            service.saveAddress(dto);
        }catch (Exception e ){
            e.printStackTrace();
            throw  e;

        }
        return new ResponseData();
    }

    @RequestMapping(value = "/address/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Address> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/address/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Address> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}