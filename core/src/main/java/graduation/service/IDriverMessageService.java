package graduation.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import graduation.dto.DriverMessage;

import java.util.List;

public interface IDriverMessageService extends IBaseService<DriverMessage>, ProxySelf<IDriverMessageService>{

    void addMessage(DriverMessage dto);

    List<DriverMessage> selectPass(DriverMessage dto, int page, int pageSize);

    List<DriverMessage> selectUnPass(DriverMessage dto, int page, int pageSize);

    boolean setPass(List<DriverMessage> dto);

    boolean setUnPass(List<DriverMessage> dto);

    List<DriverMessage>  isAuthenticated(DriverMessage dto);

    void updateMessageById(DriverMessage driverMessage);

    List<DriverMessage> selectMessage(DriverMessage dto, int page, int pageSize);
}