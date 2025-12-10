package com.example.saleCampaign.Repository;

import com.example.saleCampaign.Model.Campaign;
import com.example.saleCampaign.Model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface CampaignRepo extends JpaRepository<Campaign,Long> {
    @Query(value = "SELECT * FROM campaign WHERE start_date = :date", nativeQuery = true)
    List<Campaign> findByStartDate(LocalDate date);
    @Query(value = "SELECT * FROM campaign WHERE end_date = :date", nativeQuery = true)
    List<Campaign> findByEndDate(LocalDate date);
    @Modifying
    @Transactional
    @Query(value = "delete FROM campaign WHERE end_date = :date", nativeQuery = true)
    void deleteByEndDate(LocalDate date);

    @Modifying
    @Transactional
    @Query(value = "delete from campaign_discount where campaign_id in (select id from campaign where end_date = :date)", nativeQuery = true)
    void deleteCampaignDiscountByEndDate(LocalDate date);

}
