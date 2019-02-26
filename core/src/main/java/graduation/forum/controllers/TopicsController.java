package graduation.forum.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.forum.dto.Topics;
import graduation.forum.service.ITopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class TopicsController extends BaseController {

    @Autowired
    private ITopicsService service;


    @RequestMapping(value = "/topics/query")
    @ResponseBody
    public ResponseData query(Topics dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<Topics> topics = service.selectTopics(requestContext, dto, page, pageSize);
        int index = 0;
        for (Topics bean : topics) {
            if (bean.getUserCall().length() > 6) {
                topics.get(index).setUserCall(bean.getUserCall().substring(0, 5) + "..");
            }
            index++;
        }
        return new ResponseData(topics);
    }

    @RequestMapping(value = "/topics/query/{topicId}")
    @ResponseBody
    public ResponseData queryByTopicId(Topics dto, HttpServletRequest request, @PathVariable Long topicId) {
        IRequest requestContext = createRequestContext(request);
        dto.setTopicId(topicId);
        List<Topics> topics = service.selectTopics(requestContext, dto, 1, 1);
        int index = 0;
        for (Topics bean : topics) {
            if (bean.getUserCall().length() > 6) {
                topics.get(index).setUserCall(bean.getUserCall().substring(0, 5) + "..");
            }
            index++;
        }
        return new ResponseData(topics);
    }

    @RequestMapping(value = "/topics/add")
    @ResponseBody
    public ResponseData addTopics(@RequestBody Topics dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            HttpSession session = request.getSession();
            //得到用户id
            Long userId = (Long) session.getAttribute("userId");
            dto.setUserId(userId);
            service.addTopics(dto);
            return responseData;
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/topics/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Topics> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/topics/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Topics> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}