package com.example.saleCampaign.Controller;

import com.example.saleCampaign.Model.Campaign;
import com.example.saleCampaign.Service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    @Autowired
    CampaignService campaignService;

    @PostMapping("/addCampaign")
    public ResponseEntity<?> addCampaign(@RequestBody Campaign campaign)
    {
        return campaignService.addCampaign(campaign);
    }
    @PutMapping("/startCampaign")
    public ResponseEntity<?> startCampaign() {
        return campaignService.startCampaign();
    }
    @PutMapping("/endCampaign")
    public void endCampaign() {
        campaignService.endCampaign();
    }
}

