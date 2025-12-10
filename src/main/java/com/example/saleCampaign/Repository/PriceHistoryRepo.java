package com.example.saleCampaign.Repository;

import com.example.saleCampaign.Model.PriceHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PriceHistoryRepo extends JpaRepository<PriceHistory,Long> {
    @Query(value = "select * from Price_History where product_id=:product_id ",nativeQuery = true)
    public List<PriceHistory> findByProductId(long product_id);

    @Modifying
    @Transactional
    @Query(value = "delete from Price_History where end_date = :endDate", nativeQuery = true)
    void deleteByEndDate(LocalDate endDate);

    @Query(value = "select * from Price_History where end_date=:endDate ",nativeQuery = true)
    public List<PriceHistory> findByEndDate(LocalDate endDate);
}
