package com.zenhome.repository;

import com.zenhome.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Integer> {

    @Query(value = "select c from Consumption  c where c.reportedAt > :from and c.reportedAt<=now()")
    List<Consumption> getConsumptionByDuration(@Param("from") OffsetDateTime from);

}
