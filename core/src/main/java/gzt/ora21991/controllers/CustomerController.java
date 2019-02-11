package gzt.ora21991.controllers;

import com.hand.hap.attachment.dto.Attachment;
import com.hand.hap.attachment.service.IAttachmentService;
import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.system.service.IProfileService;
import gzt.ora21991.dto.CustomerContact;
import gzt.ora21991.dto.Product;
import gzt.ora21991.service.ICustomerContactService;
import gzt.ora21991.service.IProductService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import gzt.ora21991.dto.Customer;
import gzt.ora21991.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController extends BaseController {

    @Autowired
    private ICustomerService service;
    @Autowired
    private ICustomerContactService customerContactService;
    @Autowired
    private IProductService productService;
    @Autowired
    ISysCodeRuleProcessService codeRuleProcessService;
    @Autowired
    IAttachmentService attachmentService;
    @Autowired
    private IProfileService profileService;



    @RequestMapping(value = "/hcrm/customer/query")
    @ResponseBody
    public ResponseData query(Customer dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        if (!"ALL".equals(profileService.getProfileValue(requestContext,"HCRM_CUSTOMER"))){
            dto.setCreatedBy(requestContext.getUserId());
        }
        return new ResponseData(service.selectALLCustomer(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcrm/customer/query/{customerId}")
    @ResponseBody
    public ResponseData queryByCustomerId(Customer dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                          HttpServletRequest request,
                                          @PathVariable Long customerId) {
        IRequest requestContext = createRequestContext(request);
        dto.setCustomerId(customerId);
        List<Customer> customers=service.selectByCustomerId(dto);
        Long parentCustomersId=null;
        //获取上级客户的id
        if(customers.size()>0){
            parentCustomersId=customers.get(0).getParentCustomersId();
        }
        /*有上级客户的时候*/
        if(parentCustomersId!=null){
        //获取上级客户的名字
        dto.setCustomerId(parentCustomersId);
        List<Customer> parentCustomers=service.selectByCustomerId(dto);
        String parentCustomer="无";
         /*上级客户没被删除*/
        if(parentCustomers.size()>0){
        parentCustomer=parentCustomers.get(0).getFullName();
        }
        //把上级客户的名字加到List中
        customers.get(0).setParentCustomerName(parentCustomer);
        }
        //新建的时候customerId=-1
        if(customerId==-1L){
            Map<String, String> map = new HashMap<>();
            /**根据设置好的编码规则获取员工编码*/
            try {
                Customer customer=new Customer();
                customer.setCustomerCode(codeRuleProcessService.getRuleCode("ORA_21991_CUSTOMER_CODE", map));
                customers.add(customer);
            } catch (CodeRuleException e) {
                e.printStackTrace();
            }

        }
        return new ResponseData(customers);
    }

    @RequestMapping(value = "/hcrm/customer/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Customer> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcrm/customer/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Customer> dto) {
        try {
            //级联删除
            for(Customer customer:dto){
                productService.deleteByCustomerId(customer.getCustomerId());
                customerContactService.deleteByCustomerId(customer.getCustomerId());
            }
            service.batchDelete(dto);
        }catch (Exception e){
            throw  e;
        }
        return new ResponseData();
    }

    /**
     * @param request
     * @param
     * @return 如何接受 submitForm  用List 接受
     * @RequestBody
     */
    @RequestMapping(
            value = {"/gzt/ora/21991/customer/submit/v2"}
    )
    @ResponseBody
    public ResponseData submitForm(@RequestBody List<Customer> customers, BindingResult result, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        ResponseData responseData=new ResponseData();
        if(customers.get(0).getIsListed()==null){
            customers.get(0).setIsListed(" ");
        }
        /**根据customerCode获取附件*/
        Attachment attachments= attachmentService.selectAttachByCodeAndKey(iRequest,null,customers.get(0).getCustomerCode());
        if (attachments!=null){
            return new ResponseData(service.update(customers,iRequest));
        }else {
            responseData.setTotal(1L);
            responseData.setRows(customers);
            responseData.setSuccess(false);
            responseData.setMessage("保存前请上传附件说明！");
            return responseData;
        }
    }

}