package tw.idv.rainbow.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.rainbow.common.ApiResponse;
import tw.idv.rainbow.web.dto.CampaignDTO;
import tw.idv.rainbow.web.dto.ConnectLogDTO;
import tw.idv.rainbow.web.service.DeviceService;

import java.util.List;

@RestController
@RequestMapping("device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public ApiResponse getAll() {

        List<ConnectLogDTO> devices = deviceService.getDevice();

        return ApiResponse.success(devices);
    }

    @GetMapping("{id}")
    public ApiResponse getOne(@PathVariable Long id){

        List<ConnectLogDTO> devices = deviceService.getOneDeviceLog(id);

        return ApiResponse.success(devices);
    }
}
