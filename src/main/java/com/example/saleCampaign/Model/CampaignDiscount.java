package com.example.saleCampaign.Model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

@Embeddable
@Table(name = "campaign_discount")
public class CampaignDiscount {
    long product_id;
    double discount;

    public CampaignDiscount() {
    }

    public CampaignDiscount(long product_id, double discount) {
        this.product_id = product_id;
        this.discount = discount;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
