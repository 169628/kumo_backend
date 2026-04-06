package tw.idv.rainbow.web.service;

import tw.idv.rainbow.web.dto.CampaignDTO;
import tw.idv.rainbow.web.entity.Campaigns;

import java.util.List;

public interface CampaignService {

    String create(Campaigns campaign);

    List<CampaignDTO> getCampaign();

    CampaignDTO getOneCampaign(Long campaignId);

    String delete(Long campaignId);

    String update(Long campaignId, Campaigns campaign);

    String toggleEnable(Long campaignId);
}
