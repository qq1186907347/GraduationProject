package graduation.controllers;

import com.hand.hap.attachment.dto.Attachment;
import com.hand.hap.attachment.service.IAttachmentService;
import graduation.dto.DriverCar;
import graduation.service.IDriverCarService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.DriverMessage;
import graduation.service.IDriverMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class DriverMessageController extends BaseController {

    @Autowired
    private IDriverMessageService service;
    @Autowired
    private IDriverCarService driverCarService;
    @Autowired
    IAttachmentService attachmentService;


    @RequestMapping(value = "/driver/message/query")
    @ResponseBody
    public ResponseData query(DriverMessage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessage(dto,page,pageSize));
    }

    @RequestMapping(value = "/driver/message/query/pass")
    @ResponseBody
    public ResponseData queryPass(DriverMessage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectPass(dto, page, pageSize));
    }

    @RequestMapping(value = "/driver/message/query/{driverId}/{messageStatus}")
    @ResponseBody
    public ResponseData query(DriverMessage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long driverId,@PathVariable Long messageStatus) {
        IRequest requestContext = createRequestContext(request);
        dto.setDriverId(driverId);
        dto.setMessageStatus(messageStatus);
        return new ResponseData(service.isAuthenticated(dto));
    }

    @RequestMapping(value = "/driver/message/queryAdmin/{driverId}")
    @ResponseBody
    public ResponseData queryAdmin(DriverMessage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long driverId) {
        IRequest requestContext = createRequestContext(request);
        dto.setDriverId(driverId);
        return new ResponseData(service.selectMessage(dto,1,1));
    }

    @RequestMapping(value = "/driver/message/refuse/{driverId}")
    @ResponseBody
    public ResponseData queryRefuse(DriverMessage dto, HttpServletRequest request, @PathVariable Long driverId) {
        IRequest requestContext = createRequestContext(request);
        dto.setMessageStatus(2L);
        dto.setDriverId(driverId);
        return new ResponseData(service.isAuthenticated(dto));
    }

    /**
     * 已经完成认证的司机
     */
    @RequestMapping(value = "/driver/message/query2/{driverId}")
    @ResponseBody
    public ResponseData query2(DriverMessage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                               @PathVariable Long driverId) {
        IRequest requestContext = createRequestContext(request);
        dto.setMessageStatus(1L);
        dto.setDriverId(driverId);
        return new ResponseData(service.isAuthenticated(dto));
    }


    @RequestMapping(value = "/driver/message/query/wait")
    @ResponseBody
    public ResponseData queryWait(DriverMessage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectUnPass(dto, page, pageSize));
    }

    @RequestMapping(value = "/driver/message/query/unpass")
    @ResponseBody
    public ResponseData queryUnPass(DriverMessage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setMessageStatus(2L);
        return new ResponseData(service.selectMessage(dto, page, pageSize));
    }

    @RequestMapping(value = "/driver/message/add")
    @ResponseBody
    public ResponseData addMessage(@RequestBody List<DriverMessage> dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            HttpSession session = request.getSession();
            //得到司机id
            Long driverId = (Long) session.getAttribute("driverId");
            DriverMessage driverMessage = new DriverMessage();
            driverMessage = dto.get(0);
            //车辆信息未填写
            if (driverMessage.getCarList() == null) {
                DriverCar bean=new DriverCar();
                if(driverId==null){
                    driverId=-1L;
                }
                bean.setDriverId(driverId);
                List<DriverCar> driverCars=driverCarService.selectCars(bean);
                if(driverCars.size()==0){
                    responseData.setRows(driverCars);
                    responseData.setSuccess(false);
                    responseData.setMessage("请填写车辆信息！");
                    return responseData;
                }
            }
            /**根据driverId获取附件*/
            Attachment attCar = attachmentService.selectAttachByCodeAndKey(requestContext, "CAR_REAL_NAME", String.valueOf(driverId));
            Attachment attDriver = attachmentService.selectAttachByCodeAndKey(requestContext, "DRIVER_REAL_NAME", String.valueOf(driverId));
            //车辆图片没上传
            if (attCar == null) {
                responseData.setSuccess(false);
                responseData.setMessage("请上传车辆图片！");
                return responseData;
            }
            //司机实名认证图片
            if (attDriver == null) {
                responseData.setSuccess(false);
                responseData.setMessage("请上传司机实名认证图片！");
                return responseData;
            }
            driverMessage.setDriverId(driverId);
            driverMessage.setMessageStatus(0L);
            if(driverMessage.getMessageId()!=null){
                driverMessage.setMessageStatus(0L);
                service.updateMessageById(driverMessage);
            }else{
                service.addMessage(driverMessage);
            }

            return new ResponseData(dto);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/driver/message/set/pass")
    @ResponseBody
    public ResponseData setPass(@RequestBody List<DriverMessage> dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.setPass(dto));
    }

    @RequestMapping(value = "/driver/message/set/unpass")
    @ResponseBody
    public ResponseData setUnPass(@RequestBody List<DriverMessage> dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.setUnPass(dto));
    }

    @RequestMapping(value = "/driver/message/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<DriverMessage> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/driver/message/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DriverMessage> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/driver/message/isAuthenticated")
    @ResponseBody
    public ResponseData isAuthenticated(HttpServletRequest request, @RequestBody DriverMessage dto) {
        ResponseData responseData = new ResponseData();
        //得到司机id
        HttpSession session = request.getSession();
        Long driverId = (Long) session.getAttribute("driverId");
        dto.setDriverId(driverId);
        dto.setMessageStatus(0L);
        //是否存在待实名认证的信息
        List<DriverMessage> driverMessages = service.isAuthenticated(dto);
        dto.setMessageStatus(1L);
        //已经通过实名认证了
        List<DriverMessage> driverMessages3 = service.isAuthenticated(dto);
        dto.setMessageStatus(2L);
        //已经拒绝的实名
        List<DriverMessage> driverMessages4 = service.isAuthenticated(dto);

        if (driverMessages4.size() > 0) {
            responseData.setSuccess(false);
            responseData.setCode(String.valueOf(driverMessages4.get(0).getMessageId()));
            responseData.setTotal(driverId);
            responseData.setMessage("您的实名被拒绝了");
        } else if (driverMessages.size() == 0 && driverMessages3.size() == 0) {
            responseData.setSuccess(false);
            //把driverId返回前端页面
            responseData.setTotal(driverId);
            responseData.setMessage("您还未实名认证");
        } else {
            dto.setMessageStatus(1L);
            //是否存在审核已通过的数据
            List<DriverMessage> driverMessages2 = service.isAuthenticated(dto);
            if (driverMessages2.size() == 0) {
                responseData.setSuccess(false);
                //把driverId返回前端页面
                responseData.setTotal(driverId);
                responseData.setMessage("实名审核中，请耐心等待");
            }
        }
        responseData.setTotal(driverId);
        return responseData;
    }
}