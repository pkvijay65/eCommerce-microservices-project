package com.ecommerce.inventoryservice.controller;


import com.ecommerce.inventoryservice.dto.InventoryResponse;
import com.ecommerce.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
  private final InventoryService inventoryService;

  @GetMapping
  public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCode) {
    log.info("Received inventory check request for skuCode: {}", skuCode);
    return ResponseEntity.ok(inventoryService.isInStock(skuCode));
  }

}
