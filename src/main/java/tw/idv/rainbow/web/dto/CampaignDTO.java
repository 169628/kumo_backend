package tw.idv.rainbow.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.rainbow.web.entity.Campaigns;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO {

    private Integer no;
    private Long campaignId;
    private String brand;
    private String model;
    private String sv;
    private String tv;
    private String file;
    private Integer fileSize;
    private Boolean isTestMode;
    private List<String> testList;
    private String downloadBy;
    private Boolean isEnabled;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "GMT+8")
    private Timestamp createAt;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "GMT+8")
    private Timestamp updateAt;
    private Boolean isDeleted;

    public static CampaignDTO toDTO(Campaigns campaign) {
        CampaignDTO dto = new CampaignDTO();
        dto.setNo(campaign.getNo());
        dto.setCampaignId(campaign.getCampaignId());
        dto.setBrand(campaign.getBrand());
        dto.setModel(campaign.getModel());
        dto.setSv(campaign.getSv());
        dto.setTv(campaign.getTv());
        dto.setFile(campaign.getFile());
        dto.setFileSize(campaign.getFileSize());
        dto.setIsTestMode(campaign.getIsTestMode());
        dto.setTestList(campaign.getTestList());
        dto.setDownloadBy(campaign.getDownloadBy().getContent());
        dto.setIsEnabled(campaign.getIsEnabled());
        dto.setCreateAt(campaign.getCreateAt());
        dto.setUpdateAt(campaign.getUpdateAt());
        dto.setIsDeleted(campaign.getIsDeleted());
        return dto;
    }
}
