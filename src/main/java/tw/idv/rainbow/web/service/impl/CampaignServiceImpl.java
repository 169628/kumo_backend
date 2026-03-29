package tw.idv.rainbow.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.rainbow.web.dto.CampaignDTO;
import tw.idv.rainbow.web.entity.Campaigns;
import tw.idv.rainbow.web.repository.CampaignsRepository;
import tw.idv.rainbow.web.service.CampaignService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignsRepository repository;

    @Override
    public String create(Campaigns campaign) {
        String brand = campaign.getBrand();
        String model = campaign.getModel();
        String sv = campaign.getSv();
        String tv = campaign.getTv();

        if (brand == null) {
            return "Brand is required";
        } else if (brand.length() < 2 || brand.length() > 20) {
            return "Brand must be at least 2 characters and maximum 20 characters";
        }

        if (model == null) {
            return "Model is required";
        } else if (model.length() < 2 || model.length() > 10) {
            return "Model must be at least 2 characters and maximum 10 characters";
        }

        if (sv == null) {
            return "Source version is required";
        } else if (sv.length() < 2 || sv.length() > 10) {
            return "Source version must be at least 2 characters and maximum 10 characters";
        }

        if (tv == null) {
            return "Target version is required";
        } else if (tv.length() < 2 || tv.length() > 10) {
            return "Target version must be at least 2 characters and maximum 10 characters";
        }

        campaign = repository.save(campaign);
        Long id = campaign.getCampaignId();
        if (id == null) {
            return "Create failed";
        }

        return null;
    }

    @Override
    public List<CampaignDTO> getCampaign() {
        List<Campaigns> campaignList = repository.findByIsDeletedIsFalseOrderByCreateAtDesc();
        List<CampaignDTO> dtoList = new ArrayList<>();
        for(Campaigns campaign : campaignList){
            CampaignDTO dto = CampaignDTO.toDTO(campaign);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public CampaignDTO getOneCampaign(Long campaignId) {
        Campaigns campaign = repository.findByCampaignId(campaignId);
        if(campaign == null || campaign.getIsDeleted()) {
            return null;
        }
        return CampaignDTO.toDTO(campaign);
    }

    @Override
    public String delete(Long campaignId) {
        if(campaignId == null) {
            return "campaign id is required";
        }
        int count = repository.deleteByCampaignId(campaignId);
        if (count != 1) {
            return "Delete campaign failed";
        }
        return null;
    }

    @Override
    public String update(Long campaignId, Campaigns newCampaign) {
        String brand = newCampaign.getBrand();
        String model = newCampaign.getModel();
        String sv = newCampaign.getSv();
        String tv = newCampaign.getTv();

        if(campaignId == null) {
            return "Campaign id is required";
        }

        if (brand == null) {
            return "Brand is required";
        } else if (brand.length() < 2 || brand.length() > 20) {
            return "Brand must be at least 2 characters and maximum 20 characters";
        }

        if (model == null) {
            return "Model is required";
        } else if (model.length() < 2 || model.length() > 10) {
            return "Model must be at least 2 characters and maximum 10 characters";
        }

        if (sv == null) {
            return "Source version is required";
        } else if (sv.length() < 2 || sv.length() > 10) {
            return "Source version must be at least 2 characters and maximum 10 characters";
        }

        if (tv == null) {
            return "Target version is required";
        } else if (tv.length() < 2 || tv.length() > 10) {
            return "Target version must be at least 2 characters and maximum 10 characters";
        }

        Campaigns campaign = repository.findByCampaignId(campaignId);
        if(campaign == null){
            return "can not find the campaign";
        }
        campaign.setIsEnabled(newCampaign.getIsEnabled());
        campaign.setBrand(newCampaign.getBrand());
        campaign.setModel(newCampaign.getModel());
        campaign.setSv(newCampaign.getSv());
        campaign.setTv(newCampaign.getTv());
        campaign.setFile(newCampaign.getFile());
        campaign.setFileSize(newCampaign.getFileSize());
        campaign.setIsTestMode(newCampaign.getIsTestMode());
        campaign.setTestList(newCampaign.getTestList());
        campaign.setDownloadById(newCampaign.getDownloadById());
        campaign = repository.save(campaign);
        return null;
    }
}
