package com.example.saleCampaign.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "campaign")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;

    @ElementCollection
    @CollectionTable(
            name = "campaign_discount",
            joinColumns = @JoinColumn(name = "campaign_id")
    )
    @JsonProperty("campaignDiscount")

    List<CampaignDiscount> compaignDiscount=new ArrayList<>();


    public List<CampaignDiscount> getCompaignDiscount() {
        return compaignDiscount;
    }

    public void setCompaignDiscount(List<CampaignDiscount> compaignDiscount) {
        this.compaignDiscount = compaignDiscount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
