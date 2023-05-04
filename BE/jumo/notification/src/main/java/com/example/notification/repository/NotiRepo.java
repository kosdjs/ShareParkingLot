package com.example.notification.repository;


import com.example.notification.entity.FcmToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiRepo extends CrudRepository<FcmToken, Long> {

}
