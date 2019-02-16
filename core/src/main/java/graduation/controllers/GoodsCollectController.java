package graduation.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.GoodsCollect;
import graduation.service.IGoodsCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class GoodsCollectController extends BaseController {

    @Autowired
    private IGoodsCollectService service;

    @RequestMapping(value = "/goods/collect/query")
    @ResponseBody
    public ResponseData query(GoodsCollect dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                              HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        return new ResponseData(service.selectCollect(dto));
    }

    @RequestMapping(value = "/goods/collect/query/{collectId}")
    @ResponseBody
    public ResponseData queryByCollectId(GoodsCollect dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                              HttpServletRequest request,
                              @PathVariable Long collectId) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到用户id
        Long userId = (Long) session.getAttribute("userId");
        dto.setUserId(userId);
        dto.setCollectId(collectId);
        return new ResponseData(service.selectCollect(dto));
    }

    @RequestMapping(value = "/goods/collect/add")
    @ResponseBody
    public ResponseData collectAdd(@RequestBody List<GoodsCollect> dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        try {
            HttpSession session = request.getSession();
            //得到用户id
            Long userId = (Long) session.getAttribute("userId");
            GoodsCollect goodsCollect = dto.get(0);
            goodsCollect.setUserId(userId);
            service.addCollect(goodsCollect);
            return new ResponseData(dto);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/goods/collect/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GoodsCollect> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/goods/collect/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GoodsCollect> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}