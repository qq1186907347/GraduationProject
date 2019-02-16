package graduation.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.Goods;
import graduation.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class GoodsController extends BaseController{

    @Autowired
    private IGoodsService service;


    @RequestMapping(value = "/goods/query/{collectId}")
    @ResponseBody
    public ResponseData query(Goods dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long collectId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectByCollectId(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/goods/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Goods> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/goods/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Goods> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }