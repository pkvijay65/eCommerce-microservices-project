package com.ecommerce.orderservice.service;


import com.ecommerce.orderservice.dto.InventoryResponse;
import com.ecommerce.orderservice.dto.OrderLineItemsDto;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderLineItems;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final WebClient.Builder webClientBuilder;

  public String placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
        .stream()
        .map(this::mapToDto)
        .toList();

    order.setOrderLineItemsList(orderLineItems);

    List<String> skuCodes = order.getOrderLineItemsList().stream()
        .map(OrderLineItems::getSkuCode)
        .toList();

    // Call Inventory Service, and place order if product is in stock
      InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
          .uri("http://localhost:8081/api/inventory",
              uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
          .retrieve()
          .bodyToMono(InventoryResponse[].class)
          .block();

    boolean allProductsInStock = inventoryResponseArray != null && inventoryResponseArray.length > 0 && Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);


      if (allProductsInStock) {
        orderRepository.save(order);
        return "Order Placed";
      } else {
        throw new IllegalArgumentException("Product is not in stock, please try again later");
      }
    }

  private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
    OrderLineItems orderLineItems = new OrderLineItems();
    orderLineItems.setPrice(orderLineItemsDto.getPrice());
    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    return orderLineItems;
  }
}