package tw.idv.rainbow.web.service;

import org.springframework.web.multipart.MultipartFile;
import tw.idv.rainbow.web.dto.CampaignDTO;
import tw.idv.rainbow.web.entity.Campaigns;

import java.util.List;

public interface CampaignService {

    String create(Campaigns campaign, MultipartFile file);

    List<CampaignDTO> getCampaign();

    CampaignDTO getOneCampaign(Long campaignId);

    String delete(Long campaignId);

    String update(Long campaignId, Campaigns campaign, MultipartFile file);

    String toggleEnable(Long campaignId);
}
