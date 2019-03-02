package graduation.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.RefuseResult;
import graduation.service.IRefuseResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class RefuseResultController extends BaseController {

    @Autowired
    private IRefuseResultService service;


    @RequestMapping(value = "/refuse/result/query")
    @ResponseBody
    public ResponseData query(RefuseResult dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/refuse/result/query/{messageId}")
    @ResponseBody
    public ResponseData queryByMessageId(@RequestBody RefuseResult dto, HttpServletRequest request, @PathVariable Long messageId) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            dto.setMessageId(messageId);
            List<RefuseResult> refuseResults = service.selectRefuse(dto);
            responseData.setMessage(refuseResults.get(0).getRefuseContent());
        } catch (Exception e) {
            throw e;
        }
        return responseData;
    }

    @RequestMapping(value = "/refuse/result/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<RefuseResult> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/refuse/result/add")
    @ResponseBody
    public ResponseData addResult(@RequestBody RefuseResult dto, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            service.addResult(dto);
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setMessage("审核不通过失败，请联系管理员！");
        }
        return responseData;
    }

    @RequestMapping(value = "/refuse/result/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<RefuseResult> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}