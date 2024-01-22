package com.cortadai.orderservice.controller;

import com.cortadai.basedomain.dto.OrderDto;
import com.cortadai.basedomain.dto.OrderEventDto;
import com.cortadai.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody OrderDto orderDto){
        orderDto.setOrderId(UUID.randomUUID().toString());
        OrderEventDto orderEventDto = new OrderEventDto();
        orderEventDto.setStatus("PENDING");
        orderEventDto.setMessage("order status is in pending state");
        orderEventDto.setOrder(orderDto);
        orderProducer.sendMessage(orderEventDto);
        return "Order placed successfully";
    }

}
