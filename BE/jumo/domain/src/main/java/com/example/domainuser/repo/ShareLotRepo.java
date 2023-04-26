package com.example.domainuser.repo;

import com.example.domainparking.entity.ShareLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareLotRepo extends JpaRepository<ShareLot, Long> {

}
