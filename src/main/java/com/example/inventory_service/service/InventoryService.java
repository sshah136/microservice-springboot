package com.example.inventory_service.service;

import com.example.inventory_service.repository.InventoryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku, Integer quantity) {
        return inventoryRepository.existsBySkuAndQuantityIsGreaterThanEqual(sku, quantity);
    }
}