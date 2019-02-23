package graduation.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.User2;
import graduation.service.IUser2Service;
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

    @RequestMapping(value = "/user/getUserId")
    @ResponseBody
    public ResponseData getUserId(User2 dto,HttpServletRequest request) {
        HttpSession session = request.getSession();
        ResponseData responseData=new ResponseData();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        if(userId!=null){
            responseData.setMessage(String.valueOf(userId));

        }else {
            responseData.setMessage("未登录，请登录");
            responseData.setSuccess(false);

        }
        return responseData;
    }

    @RequestMapping(value = "/login/user/register")
    @ResponseBody
    public ResponseData register(@RequestBody User2 dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        try {
            User2 user = service.selectByUserName(dto);
            if (user != null) {
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
            throw e;
        }
        return new ResponseData();
    }

    @RequestMapping(value = "/login/user/login")
    @ResponseBody
    public ResponseData login(@RequestBody User2 dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        try {
            User2 user = service.checkLogin(dto);
            if (user == null) {
                ResponseData responseData = new ResponseData();
                responseData.setTotal(1L);
                responseData.setSuccess(false);
                responseData.setMessage("账号或者密码不正确!");
                return responseData;
            } else {
                //使用request对象的getSession()获取session，如果session不存在则创建一个
                HttpSession session = request.getSession();
                //将数据存储到session中
                session.setAttribute("userId", user.getUserId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
    @RequestMapping(value = "/user/setOut")
    @ResponseBody
    public ResponseData setOut(@RequestBody User2 dto, HttpServletRequest request) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        HttpSession session = request.getSession();
        //将数据存储到session中
        session.setAttribute("userId",null);
        return new ResponseData();
    }
}