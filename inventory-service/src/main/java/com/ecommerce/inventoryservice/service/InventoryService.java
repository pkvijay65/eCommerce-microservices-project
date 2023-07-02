package com.ecommerce.inventoryservice.service;


import com.ecommerce.inventoryservice.dto.InventoryResponse;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
  private final InventoryRepository inventoryRepository;

  public List<InventoryResponse> isInStock(List<String> skuCode) {
    log.info("Checking Inventory");
    return inventoryRepository.findBySkuCodeIn(skuCode).stream()
      .map(inventory ->
        InventoryResponse.builder()
          .skuCode(inventory.getSkuCode())
          .isInStock(inventory.getQuantity() > 0)
          .build()
      ).toList();
  }

}
