package com.dev.application.repository;


import com.dev.application.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository  extends JpaRepository<Asset, Long> {
    Asset findBySerialNumber(String serialNumber);
}


