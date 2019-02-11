package graduation.user.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.user.dto.User2;
import graduation.user.service.IUser2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class User2Controller extends BaseController {

    @Autowired
    private IUser2Service service;


    @RequestMapping(value = "/login/user/query")
    @ResponseBody
    public ResponseData query(User2 dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/login/user/register")
    @ResponseBody
    public ResponseData register(@RequestBody User2 dto,HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        try {
            User2 user = service.selectByUserName(dto);
            if (user!=null) {
                ResponseData responseData = new ResponseData();
                responseData.setTotal(1L);
                responseData.setSuccess(false);
                responseData.setMessage("该用户已注册！");
                return responseData;
            } else {
                service.register(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw  e;
        }
        return new ResponseData();
    }

    @RequestMapping(value = "/login/user/login")
    @ResponseBody
    public ResponseData login(@RequestBody User2 dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        try {
            User2 user = service.checkLogin(dto);
            if (user==null) {
                ResponseData responseData = new ResponseData();
                responseData.setTotal(1L);
                responseData.setSuccess(false);
                responseData.setMessage("账号或者密码不正确!");
                return responseData;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw  e;
        }
        return new ResponseData();
    }

    @RequestMapping(value = "/login/user/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<User2> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/login/user/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<User2> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}