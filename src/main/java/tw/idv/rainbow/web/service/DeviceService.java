package tw.idv.rainbow.web.service;

import tw.idv.rainbow.web.dto.ConnectLogDTO;
import tw.idv.rainbow.web.dto.ConnectResponseDTO;
import tw.idv.rainbow.web.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {

    ConnectResponseDTO connect(DeviceDTO deviceDTO);

    List<ConnectLogDTO> getDevice();

    List<ConnectLogDTO> getOneDeviceLog(Long deviceId);
}
