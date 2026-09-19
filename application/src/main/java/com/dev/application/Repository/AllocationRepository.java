package com.dev.application.Repository;

import com.dev.application.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long>{
    List<Allocation> findUserById(Long userId);
    List<Allocation> findAssetById(Long assetId);
}
