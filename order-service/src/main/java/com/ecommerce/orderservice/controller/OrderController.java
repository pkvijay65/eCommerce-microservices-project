package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
  public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
    log.info("Placing Order");
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(orderRequest));
  }

  public ResponseEntity<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
    log.info("Cannot Place Order, hence Executing Fallback logic");
    return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("We are updating our inventory, please try after sometime");
  }
}
