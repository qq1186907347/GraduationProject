package graduation.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.CarRepair;
import graduation.service.ICarRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class CarRepairController extends BaseController{

    @Autowired
    private ICarRepairService service;


    @RequestMapping(value = "/car/repair/query")
    @ResponseBody
    public ResponseData query(CarRepair dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        //得到司机id
        Long driverId = (Long) session.getAttribute("driverId");
        dto.setDriverId(driverId);
        return new ResponseData(service.selectCarRepair(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/car/repair/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CarRepair> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        HttpSession session = request.getSession();
        //得到司机id
        Long driverId = (Long) session.getAttribute("driverId");
        for(int i=0;i<dto.size();i++){
            dto.get(i).setDriverId(driverId);
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/car/repair/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CarRepair> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }