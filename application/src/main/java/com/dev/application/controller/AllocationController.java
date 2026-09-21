package com.dev.application.controller;

import com.dev.application.entity.Allocation;
import com.dev.application.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/allocations")
@CrossOrigin(origins = "*")
public class AllocationController {

    @Autowired
    private AllocationService allocationService;

    @PostMapping
    public Allocation createAllocation(@RequestParam Long userId, @RequestParam Long assetId) {
        return allocationService.allocateAsset(userId, assetId);
    }
}