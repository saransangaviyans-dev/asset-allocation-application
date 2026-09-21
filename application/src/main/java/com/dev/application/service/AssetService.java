package com.dev.application.service;

import com.dev.application.entity.Asset;
import com.dev.application.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset saveAsset(Asset asset) {
        if (asset.getStatus() == null) {
            asset.setStatus("AVAILABLE");
        }
        return assetRepository.save(asset);
    }
}