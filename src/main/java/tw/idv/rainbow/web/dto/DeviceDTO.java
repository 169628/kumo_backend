package tw.idv.rainbow.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.rainbow.web.entity.Campaigns;
import tw.idv.rainbow.web.entity.Devices;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    private String deviceId;
    private String sn;
    private String brand;
    private String model;
    private String sv;
    private String status;
    private String session;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "GMT+8")
    private Timestamp firstConnect;

    public static DeviceDTO toDTO(Devices device) {
        DeviceDTO dto = new DeviceDTO();
        dto.setDeviceId(String.valueOf(device.getDeviceId()));
        dto.setBrand(device.getBrand());
        dto.setModel(device.getModel());
        dto.setFirstConnect(device.getFirstConnect());
        return dto;
    }
}
