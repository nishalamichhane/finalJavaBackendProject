package com.finalProject.nisha.controllers;

import com.finalProject.nisha.dtos.OrderDto;
import com.finalProject.nisha.dtos.OrderlineDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrder() {
        return ResponseEntity.ok().body(orderService.getAllOrder());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) throws RecordNotFoundException{
        return ResponseEntity.ok().body(orderService.getOrder(id));
    }

    @PostMapping
    public ResponseEntity<Object> addOrder(@Valid @RequestBody OrderDto orderDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            OrderDto addedOrder = orderService.addOrder(orderDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedOrder.id)));
            return ResponseEntity.created(uri).body(addedOrder);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) throws RecordNotFoundException {
        OrderDto updateOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok().body(updateOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) throws RecordNotFoundException {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/totalAmount")
    public ResponseEntity<OrderDto> getTotalAmount(@PathVariable Long id) {
        OrderDto orderDto = new OrderDto();
        orderDto.id = id;
        orderDto.totalAmount = orderService.getTotalAmount(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
}