package com.dev.application.service;

import com.dev.application.entity.Allocation;
import com.dev.application.entity.Asset;
import com.dev.application.entity.AssetRequest;
import com.dev.application.entity.User;
import com.dev.application.repository.AllocationRepository;
import com.dev.application.repository.AssetRepository;
import com.dev.application.repository.AssetRequestRepository;
import com.dev.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AssetRequestService {

    @Autowired
    private AssetRequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private AllocationRepository allocationRepository;

    public AssetRequest createRequest(Long userId, String category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AssetRequest request = new AssetRequest();
        request.setUser(user);
        request.setRequestedCategory(category);
        request.setRequestDate(LocalDate.now());
        request.setRequestStatus("PENDING");

        return requestRepository.save(request);
    }

    public List<AssetRequest> getPendingRequests(Long requesterId) {
        User admin = userRepository.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"ADMIN".equals(admin.getRole())) {
            throw new RuntimeException("Access Denied: Only Admins can view pending requests");
        }

        return requestRepository.findByRequestStatus("PENDING");
    }

    public Allocation approveRequest(Long adminId, Long requestId, Long assetId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"ADMIN".equals(admin.getRole())) {
            throw new RuntimeException("Access Denied: Only Admins can approve requests");
        }

        AssetRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        if (!"AVAILABLE".equals(asset.getStatus())) {
            throw new RuntimeException("Asset is not available for allocation");
        }

        request.setRequestStatus("APPROVED");
        requestRepository.save(request);

        asset.setStatus("ALLOCATED");
        assetRepository.save(asset);

        Allocation allocation = new Allocation();
        allocation.setUser(request.getUser()); // Give it to the employee who asked!
        allocation.setAsset(asset);
        allocation.setAllocationDate(LocalDate.now());

        return allocationRepository.save(allocation);
    }
}
