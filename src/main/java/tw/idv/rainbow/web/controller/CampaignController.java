package tw.idv.rainbow.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.rainbow.common.ApiResponse;
import tw.idv.rainbow.web.dto.CampaignDTO;
import tw.idv.rainbow.web.entity.Campaigns;
import tw.idv.rainbow.web.service.CampaignService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping
    public ApiResponse getAll() {

        List<CampaignDTO> campaigns = campaignService.getCampaign();

        return ApiResponse.success(campaigns);
    }

    @GetMapping("{id}")
    public ApiResponse getOne(@PathVariable Long id){

        CampaignDTO campaignDTO = campaignService.getOneCampaign(id);

        if (campaignDTO == null){
            return ApiResponse.error("can not find the campaign");
        }
        return ApiResponse.success(campaignDTO);
    }

    @PostMapping
    public ApiResponse create(@RequestBody Campaigns campaign){
        String message = campaignService.create(campaign);
        if(message != null){
            return ApiResponse.error(message);
        }
        return ApiResponse.success(null);
    }

    @PutMapping("{id}")
    public ApiResponse put(@PathVariable Long id, @RequestBody Campaigns campaign){
        String message = campaignService.update(id,campaign);
        if(message != null){
            return ApiResponse.error(message);
        }
        return ApiResponse.success(null);
    }

    @DeleteMapping("{id}")
    public ApiResponse delete(@PathVariable Long id){
        String message = campaignService.delete(id);
        if(message != null){
            return ApiResponse.error(message);
        }
        return ApiResponse.success(null);
    }
}

