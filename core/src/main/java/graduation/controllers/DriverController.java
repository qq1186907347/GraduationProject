package graduation.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.Driver;
import graduation.service.IDriverService;
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
public class DriverController extends BaseController {

    @Autowired
    private IDriverService service;


    @RequestMapping(value = "/driver/query")
    @ResponseBody
    public ResponseData query(Driver dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/driver/register")
    @ResponseBody
    public ResponseData driverRegister(@RequestBody Driver dto, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            Driver driver = new Driver();
            //验证电话是否注册了
            driver.setPhone(dto.getPhone());
            if (service.selectByDriver(driver) != null) {
                responseData.setSuccess(false);
                responseData.setMessage("该电话已经被注册了！");
                return responseData;
            } else {
                driver = new Driver();
            }
            //验证账号是否注册了
            driver.setUserName(dto.getUserName());
            if (service.selectByDriver(driver) != null) {
                responseData.setSuccess(false);
                responseData.setMessage("该账号已经被注册了！");
                return responseData;
            } else {
                driver = new Driver();
            }
            service.selectByDriver(dto);
            service.driverRegister(dto);
            return new ResponseData(true);
        } catch (Exception e) {
            throw e;

        }
    }

    @RequestMapping(value = "/login/driver/login")
    @ResponseBody
    public ResponseData login(@RequestBody Driver dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        try {
            Driver driver = service.checkLogin(dto);
            if (driver == null) {
                ResponseData responseData = new ResponseData();
                responseData.setSuccess(false);
                responseData.setMessage("账号或者密码不正确!");
                return responseData;
            } else {
                //使用request对象的getSession()获取session，如果session不存在则创建一个
                HttpSession session = request.getSession();
                //将数据存储到session中
                session.setAttribute("driverId", driver.getDriverId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return new ResponseData();
    }


    @RequestMapping(value = "/driver/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Driver> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/driver/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Driver> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/driver/getDriverId")
    @ResponseBody
    public ResponseData getDriverId(@RequestBody Driver dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ResponseData responseData = new ResponseData();
        //得到司机id
        Long driverId = (Long) session.getAttribute("driverId");
        if (driverId != null) {
            dto.setDriverId(driverId);
            Driver driver=service.selectByDriver(dto);
            responseData.setMessage(driver.getUserName());
        } else {
            responseData.setMessage("未登录，请登录");
            responseData.setSuccess(false);
        }
        return responseData;
    }

    @RequestMapping(value = "/driver/setOut")
    @ResponseBody
    public ResponseData setOut(@RequestBody Driver dto, HttpServletRequest request) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        HttpSession session = request.getSession();
        //将数据存储到session中
        session.setAttribute("driverId",null);
        return new ResponseData();
    }
}