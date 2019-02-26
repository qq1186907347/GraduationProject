package graduation.forum.controllers;

import graduation.forum.dto.Topics;
import graduation.forum.service.ITopicsService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.forum.dto.TopicsReply;
import graduation.forum.service.ITopicsReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;

    @Controller
    public class TopicsReplyController extends BaseController{

    @Autowired
    private ITopicsReplyService service;
        @Autowired
        private ITopicsService topicsService;


    @RequestMapping(value = "/topics/reply/query/{topicId}")
    @ResponseBody
    public ResponseData queryReplyById(TopicsReply dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long topicId) {
        IRequest requestContext = createRequestContext(request);
        dto.setTopicId(topicId);
        dto.setTopicId(topicId);
        List<TopicsReply> topicsReplyList = service.selectReply(requestContext, dto, page, pageSize);
        int index = 0;
        for (TopicsReply bean : topicsReplyList) {
            if (bean.getUserCall().length() > 6) {
                topicsReplyList.get(index).setUserCall(bean.getUserCall().substring(0, 3) + "..");
            }
            index++;
        }
        return new ResponseData(topicsReplyList);
    }

        @RequestMapping(value = "/topics/reply/add")
        @ResponseBody
        public ResponseData addReply(@RequestBody TopicsReply dto, HttpServletRequest request) {
            IRequest requestContext = createRequestContext(request);
            try{
                HttpSession session = request.getSession();
                //得到用户id
                Long userId = (Long) session.getAttribute("userId");
                dto.setUserId(userId);
                service.addReply(dto);
                Topics topics=new Topics();
                topics.setLastUpdateTime(new Date());
                topics.setTopicId(dto.getTopicId());
                topicsService.setTopicsTime(topics);
            }catch (Exception e){
                throw e;
            }
            return new ResponseData();
        }
    @RequestMapping(value = "/topics/reply/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<TopicsReply> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/topics/reply/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<TopicsReply> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }