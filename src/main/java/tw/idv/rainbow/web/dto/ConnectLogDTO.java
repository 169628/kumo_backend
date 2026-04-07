package tw.idv.rainbow.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.rainbow.web.entity.ConnectLogs;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectLogDTO {
    private String sessionId;
    private String deviceId;
    private String brand;
    private String model;
    private String sn;
    private String sv;
    private String status;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "GMT+8")
    private Timestamp firstConnect;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "GMT+8")
    private Timestamp reportedAt;

    public static ConnectLogDTO toDTO(ConnectLogs connectLog){
        ConnectLogDTO dto = new ConnectLogDTO();
        dto.setSessionId(connectLog.getSessionId());
        dto.setDeviceId(String.valueOf(connectLog.getDevice().getDeviceId()));
        dto.setBrand(connectLog.getDevice().getBrand());
        dto.setModel(connectLog.getDevice().getModel());
        dto.setSn(connectLog.getDevice().getSn());
        dto.setSv(connectLog.getSv());
        dto.setStatus(connectLog.getStatus().getStatus());
        dto.setFirstConnect(connectLog.getDevice().getFirstConnect());
        dto.setReportedAt(connectLog.getReportedAt());
        return dto;
    }
}
