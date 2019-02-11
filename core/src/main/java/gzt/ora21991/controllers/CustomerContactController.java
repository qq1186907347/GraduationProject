package gzt.ora21991.controllers;

import com.hand.hap.attachment.service.IAttachmentService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import gzt.ora21991.dto.CustomerContact;
import gzt.ora21991.service.ICustomerContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerContactController extends BaseController {

    @Autowired
    private ICustomerContactService service;

    @RequestMapping(value = "/hcrm/customer/contact/query")
    @ResponseBody
    public ResponseData query(CustomerContact dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                              HttpServletRequest request,
                              @PathVariable Long customerId) {
        IRequest requestContext = createRequestContext(request);
        dto.setCustomerId(customerId);
        return new ResponseData(service.selectByCustomerId(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcrm/customer/contact/query/{customerId}")
    @ResponseBody
    public ResponseData queryByCustomerId(CustomerContact dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                          HttpServletRequest request,
                                          @PathVariable Long customerId) {
        IRequest requestContext = createRequestContext(request);
        dto.setCustomerId(customerId);
        return new ResponseData(service.selectByCustomerId(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcrm/customer/contact/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CustomerContact> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcrm/customer/contact/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CustomerContact> dto) {
        boolean badData=false;
        IRequest iRequest = createRequestContext(request);
        for (CustomerContact contact :dto){
            if(contact.getContactType().equals("HCRM_MAJOR")){
                badData=true;
            }
        }
        if(badData==true){
        ResponseData responseData= new ResponseData();
        responseData.setTotal(1L);
        CustomerContact contact=new CustomerContact();
        contact.setCustomerId(dto.get(0).getCustomerId());
        List<CustomerContact> customerContacts1=service.selectByCustomerId(iRequest,contact,1,10);
        responseData.setRows(customerContacts1);
        responseData.setSuccess(false);
        responseData.setMessage("联系人错误：主要联系人有且只有一个！，请刷新");
        return responseData;
        }else{
            service.batchDelete(dto);
            return new ResponseData();
        }
    }

    @RequestMapping(value = "/hcrm/customer/contact/checkCustomerContact")
    @ResponseBody
    public ResponseData submitForm(@RequestBody List<CustomerContact> customerContacts, BindingResult result, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        if (!hasMainConcact(customerContacts)) {
            responseData.setTotal(1L);
            responseData.setRows(customerContacts);
            responseData.setSuccess(false);
            responseData.setMessage("联系人错误：主要联系人有且只有一个！");
        } else {
            responseData.setTotal(1L);
            List<CustomerContact> customerContacts1=new ArrayList<CustomerContact>();
            responseData.setRows(customerContacts1);
            responseData.setSuccess(true);
            responseData.setMessage("sucess");
        }
        return responseData;
    }

    /**
     * 判断是否有主要联系人
     */
    boolean hasMainConcact(List<CustomerContact> customerContacts) {
        int mainConcactNum = 0;
        if (customerContacts == null) {
            return false;
        }
        for (CustomerContact contact : customerContacts) {
            if (contact.getContactType().equals("HCRM_MAJOR")) {
                mainConcactNum++;
            }
        }
        //主要联系人只有一个
        if (mainConcactNum == 1) {
            return true;
        } else {
            return false;
        }
    }

}