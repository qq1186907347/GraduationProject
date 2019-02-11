package gzt.ora21991.controllers;

import gzt.ora21991.dto.Customer;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import gzt.ora21991.dto.Product;
import gzt.ora21991.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class ProductController extends BaseController{

    @Autowired
    private IProductService service;
    @RequestMapping(value = "/hcrm/product/query/{customerId}")
    @ResponseBody
    public ResponseData query(Product dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,
                              @PathVariable Long customerId) {
        IRequest requestContext = createRequestContext(request);
        dto.setCustomerId(customerId);
        List<Product> products=service.selectByCustomerId(requestContext,dto,page,pageSize);
        return new ResponseData(products);
    }

    @RequestMapping(value = "/hcrm/product/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Product> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcrm/product/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Product> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    }