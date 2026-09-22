package com.dev.application.controller;

import com.dev.application.entity.Allocation;
import com.dev.application.entity.AssetRequest;
import com.dev.application.service.AssetRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*")
public class AssetRequestController {

    @Autowired
    private AssetRequestService requestService;

    // 1. Employee submits a ticket
    // URL: POST http://localhost:8080/api/requests?userId=2&category=Laptop
    @PostMapping
    public AssetRequest createRequest(@RequestParam Long userId, @RequestParam String category) {
        return requestService.createRequest(userId, category);
    }

    // 2. Admin checks the pending queue
    // URL: GET http://localhost:8080/api/requests/pending?adminId=1
    @GetMapping("/pending")
    public List<AssetRequest> getPendingRequests(@RequestParam Long adminId) {
        return requestService.getPendingRequests(adminId);
    }

    // 3. Admin approves the ticket and assigns a specific asset
    // URL: POST http://localhost:8080/api/requests/1/approve?adminId=1&assetId=1
    @PostMapping("/{requestId}/approve")
    public Allocation approveRequest(
            @PathVariable Long requestId,
            @RequestParam Long adminId,
            @RequestParam Long assetId) {
        return requestService.approveRequest(adminId, requestId, assetId);
    }
}
