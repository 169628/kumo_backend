package tw.idv.rainbow.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import tw.idv.rainbow.web.dto.ConnectResponseDTO;
import tw.idv.rainbow.web.dto.DeviceDTO;
import tw.idv.rainbow.web.entity.Devices;
import tw.idv.rainbow.web.service.DeviceService;

@Controller
public class ConnectController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private DeviceService deviceService;

    @MessageMapping("/device/{sn}")
    public void connect(@DestinationVariable String sn, DeviceDTO deviceDTO){
        System.out.println("後端收到");
        ConnectResponseDTO response = deviceService.connect(deviceDTO);
        if(response != null && response.getMessage() == null){
            if(response.getCampaignDTO() != null){
                response.setMessage("received");
            }
        }
        System.out.println("後端回覆");
        messagingTemplate.convertAndSend("/msg/"+sn,response);
    }
}
