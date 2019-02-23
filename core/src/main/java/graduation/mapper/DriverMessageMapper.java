package graduation.mapper;

import com.hand.hap.mybatis.common.Mapper;
import graduation.dto.DriverMessage;

import java.util.List;

public interface DriverMessageMapper extends Mapper<DriverMessage>{

    void addMessage(DriverMessage dto);

    List<DriverMessage> selectPass(DriverMessage dto);

    List<DriverMessage> selectUnPass(DriverMessage dto);

    /**
     * 是否审核通过
     * */
    boolean setMessageStatus(DriverMessage driverMessage);

    List<DriverMessage> isAuthenticated(DriverMessage dto);
}