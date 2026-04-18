package tw.idv.rainbow.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.idv.rainbow.common.ApiResult;
import tw.idv.rainbow.web.dto.CampaignDTO;
import tw.idv.rainbow.web.entity.Campaigns;
import tw.idv.rainbow.web.service.CampaignService;

import java.util.List;

@Tag(name = "Campaign Page")
@RestController
@RequestMapping("campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Operation(summary = "Get all campaign list")
    @GetMapping
    public ApiResult getAll() {

        List<CampaignDTO> campaigns = campaignService.getCampaign();

        return ApiResult.success(campaigns);
    }

    @Operation(summary = "Get one campaign", description = "The id is campaign id")
    @GetMapping("{id}")
    public ApiResult getOne(@PathVariable Long id){

        CampaignDTO campaignDTO = campaignService.getOneCampaign(id);

        if (campaignDTO == null){
            return ApiResult.error("can not find the campaign");
        }
        return ApiResult.success(campaignDTO);
    }

    @Operation(summary = "Create campaign", description = "Because create campaign need upload file, the better way to test is use kumo frontend web")
    @PostMapping
    public ApiResult create(@RequestPart Campaigns campaign, @RequestPart MultipartFile uploadData){
        String message = campaignService.create(campaign,uploadData);
        if(message != null){
            return ApiResult.error(message);
        }
        return ApiResult.success(null);
    }

    @Operation(summary = "Update one campaign", description = "Not include enable change, the id is campaign id")
    @PutMapping("{id}")
    public ApiResult put(@PathVariable Long id, @RequestPart Campaigns campaign, @RequestPart(required = false) MultipartFile uploadData){
        String message = campaignService.update(id,campaign,uploadData);
        if(message != null){
            return ApiResult.error(message);
        }
        return ApiResult.success(null);
    }

    @Operation(summary = "Delete one campaign", description = "The id is campaign id")
    @DeleteMapping("{id}")
    public ApiResult delete(@PathVariable Long id){
        String message = campaignService.delete(id);
        if(message != null){
            return ApiResult.error(message);
        }
        return ApiResult.success(null);
    }

    @Operation(summary = "Change campaign enable", description = "The id is campaign id")
    @PutMapping("enable/{id}")
    public ApiResult enable(@PathVariable Long id){
        String message = campaignService.toggleEnable(id);
        if(message != null){
            return ApiResult.error(message);
        }
        return ApiResult.success(null);
    }
}

