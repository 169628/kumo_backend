package tw.idv.rainbow.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.idv.rainbow.web.dto.CampaignDTO;
import tw.idv.rainbow.web.entity.Campaigns;
import tw.idv.rainbow.web.repository.CampaignsRepository;
import tw.idv.rainbow.web.service.CampaignService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private CampaignsRepository repository;

    @Override
    public String create(Campaigns campaign, MultipartFile file) {
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

        if(campaign.getIsTestMode() == null){
            return "Test mode is required";
        }

        if(campaign.getDownloadById() == null){
            return "Download by id is required";
        }

        if(file == null || file.isEmpty()){
            return "file is require";
        }

        // save file
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        try {
            Files.createDirectories(Paths.get(uploadDir));
            file.transferTo(Paths.get(uploadDir, filename));
            campaign.setFilePath("http://localhost:8080" + contextPath + "/file/" + filename);
            campaign.setFile(filename);
            campaign.setFileSize((int) file.getSize());
        } catch (IOException e) {
            return "File upload failed";
        }

        repository.save(campaign);

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

        Campaigns campaign = repository.findByCampaignId(campaignId);
        if(campaign == null || campaign.getIsDeleted()){
            return "Can not find the campaign";
        }
        campaign.setIsDeleted(true);
        repository.save(campaign);
        return null;
    }

    @Override
    public String update(Long campaignId, Campaigns newCampaign, MultipartFile file) {
        String brand = newCampaign.getBrand();
        String model = newCampaign.getModel();
        String sv = newCampaign.getSv();
        String tv = newCampaign.getTv();

        if(campaignId == null) {
            return "Campaign id is required";
        }

        Campaigns campaign = repository.findByCampaignId(campaignId);
        if(campaign == null || campaign.getIsDeleted()){
            return "Can not find the campaign";
        }

        if (brand != null) {
            if(brand.length() < 2 || brand.length() > 20){
                return "Brand must be at least 2 characters and maximum 20 characters";
            }
            campaign.setBrand(brand);
        }

        if (model != null) {
            if(model.length() < 2 || model.length() > 10){
                return "Model must be at least 2 characters and maximum 10 characters";
            }
            campaign.setModel(model);
        }

        if (sv != null) {
            if(sv.length() < 2 || sv.length() > 10){
                return "Source version must be at least 2 characters and maximum 10 characters";
            }
            campaign.setSv(sv);
        }

        if (tv != null) {
            if(tv.length() < 2 || tv.length() > 10){
                return "Target version must be at least 2 characters and maximum 10 characters";
            }
            campaign.setTv(tv);
        }

        if(newCampaign.getIsTestMode() != null){
            campaign.setIsTestMode(newCampaign.getIsTestMode());
        }

        if(newCampaign.getTestList() != null && !newCampaign.getTestList().isEmpty()){
            campaign.setTestList(newCampaign.getTestList());
        }

        if(newCampaign.getDownloadById() != null){
            campaign.setDownloadById(newCampaign.getDownloadById());
        }

        if(file != null){
            // save file
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            try {
                Files.createDirectories(Paths.get(uploadDir));
                file.transferTo(Paths.get(uploadDir, filename));
                campaign.setFilePath("http://localhost:8080" + contextPath + "/file/" + filename);
                campaign.setFile(filename);
                campaign.setFileSize((int) file.getSize());
            } catch (IOException e) {
                return "File upload failed";
            }
        }

        campaign.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        repository.save(campaign);

        return null;
    }

    @Override
    public String toggleEnable(Long campaignId) {
        Campaigns campaign = repository.findByCampaignId(campaignId);
        if(campaign == null || campaign.getIsDeleted()){
            return "Can not find the campaign";
        }
        campaign.setIsEnabled(!campaign.getIsEnabled());
        repository.save(campaign);
        return null;
    }

}
