package com.example.domainuser.repo;

import com.example.domainparking.entity.DayData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayDataRepo extends JpaRepository<DayData, Long> {
}
