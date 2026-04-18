package tw.idv.rainbow.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.rainbow.common.ApiResult;
import tw.idv.rainbow.web.dto.ConnectLogDTO;
import tw.idv.rainbow.web.service.DeviceService;

import java.util.List;

@Tag(name = "Device Page")
@RestController
@RequestMapping("device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @Operation(summary = "Get all device last status list")
    @GetMapping
    public ApiResult getAll() {

        List<ConnectLogDTO> devices = deviceService.getDevice();

        return ApiResult.success(devices);
    }

    @Operation(summary = "Get one device connect log", description = "The id is device id")
    @GetMapping("{id}")
    public ApiResult getOne(@PathVariable Long id){

        List<ConnectLogDTO> devices = deviceService.getOneDeviceLog(id);

        return ApiResult.success(devices);
    }
}
