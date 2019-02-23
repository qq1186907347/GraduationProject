package graduation.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import graduation.dto.DriverCar;
import graduation.service.IDriverCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DriverCarController extends BaseController {

    @Autowired
    private IDriverCarService service;


    @RequestMapping(value = "/driver/car/query/{driverId}")
    @ResponseBody
    public ResponseData query(DriverCar dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                              HttpServletRequest request,
                              @PathVariable Long driverId) {
        IRequest requestContext = createRequestContext(request);
        dto.setDriverId(driverId);
        dto.setCarTonnage(null);
        List<DriverCar> cars = service.selectCars(dto);

        return new ResponseData(cars);
    }

    @RequestMapping(value = "/driver/car/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<DriverCar> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/driver/car/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DriverCar> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}