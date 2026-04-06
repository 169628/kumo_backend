package tw.idv.rainbow.web.service;

import tw.idv.rainbow.web.dto.CampaignDTO;
import tw.idv.rainbow.web.dto.ConnectResponseDTO;
import tw.idv.rainbow.web.dto.DeviceDTO;

public interface DeviceService {

    ConnectResponseDTO connect(DeviceDTO deviceDTO);
}
