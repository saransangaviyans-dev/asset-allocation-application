package com.dev.application.service;

import com.dev.application.entity.Allocation;
import com.dev.application.entity.Asset;
import com.dev.application.entity.User;
import com.dev.application.repository.AllocationRepository;
import com.dev.application.repository.AssetRepository;
import com.dev.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AllocationService {

    @Autowired
    private AllocationRepository allocationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssetRepository assetRepository;

    public Allocation allocateAsset(Long userId, Long assetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found!"));

        if (!"AVAILABLE".equals(asset.getStatus())) {
            throw new RuntimeException("Asset is currently " + asset.getStatus());
        }

        Allocation allocation = new Allocation();
        allocation.setUser(user);
        allocation.setAsset(asset);
        allocation.setAllocationDate(LocalDate.now());

        asset.setStatus("ALLOCATED");
        assetRepository.save(asset);

        return allocationRepository.save(allocation);
    }
}