package graduation.money.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.money.dto.BalanceUser;
import graduation.money.service.IBalanceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class BalanceUserController extends BaseController {

    @Autowired
    private IBalanceUserService service;


    @RequestMapping(value = "/balance/user/query/{type}")
    @ResponseBody
    public ResponseData query(BalanceUser dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long type) {
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        IRequest requestContext = createRequestContext(request);
        dto.setType(type);
        return new ResponseData(service.selectUserBalance(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/balance/user/getBalance")
    @ResponseBody
    public ResponseData getBalance(BalanceUser dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        BalanceUser balanceUser = new BalanceUser();
        balanceUser.setUserId(userId);
        Float sum = service.getUserBalance(balanceUser);
        ResponseData responseData = new ResponseData();
        responseData.setMessage(String.valueOf(sum));
        return responseData;
    }

    @RequestMapping(value = "/balance/user/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BalanceUser> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/balance/user/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BalanceUser> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


}