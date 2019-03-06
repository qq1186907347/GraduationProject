package graduation.credit.controllers;

import graduation.credit.dto.CreditDriver;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.credit.dto.CreditUser;
import graduation.credit.service.ICreditUserService;
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
public class CreditUserController extends BaseController {

    @Autowired
    private ICreditUserService service;


    @RequestMapping(value = "/credit/user/query")
    @ResponseBody
    public ResponseData query(CreditUser dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        return new ResponseData(service.selectCreditUser(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/credit/user/getCredit")
    @ResponseBody
    public ResponseData getCredit(@RequestBody CreditUser dto,HttpServletRequest request) {
        ResponseData responseData=new ResponseData();
        Float credit=Float.valueOf(0);
        try {
            IRequest requestContext = createRequestContext(request);
            HttpSession session = request.getSession();
            //得到用户id
            Long userId = (Long) session.getAttribute("userId");
            //获取用户积分
            CreditUser creditUser = new CreditUser();
            creditUser.setUserId(userId);
            credit= service.getUserCredit(creditUser);
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

    @RequestMapping(value = "/credit/user/add")
    @ResponseBody
    public ResponseData addCredit(@RequestBody CreditUser dto,HttpServletRequest request) {
        ResponseData responseData=new ResponseData();
        try {
            IRequest requestContext = createRequestContext(request);
            HttpSession session = request.getSession();
            //得到用户id
            Long userId = (Long) session.getAttribute("userId");
            dto.setUserId(userId);
            service.addCredit(dto);
        }catch (Exception e){
            throw  e;
        }

        return responseData;
    }

    @RequestMapping(value = "/credit/user/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CreditUser> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/credit/user/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CreditUser> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}