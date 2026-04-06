package tw.idv.rainbow.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectResponseDTO {
    private String session;
    private String message;
    private CampaignDTO campaignDTO;
}
