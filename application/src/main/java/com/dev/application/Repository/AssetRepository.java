package com.dev.application.Repository;


import com.dev.application.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository  extends JpaRepository<Asset, Long> {
    Asset findBySerialNumber(String serialNumber);
}


