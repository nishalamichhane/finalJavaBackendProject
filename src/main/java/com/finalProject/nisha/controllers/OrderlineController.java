package com.finalProject.nisha.controllers;

import com.finalProject.nisha.dtos.OrderlineDto;
import com.finalProject.nisha.services.OrderlineService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orderline")
public class OrderlineController {
    private final OrderlineService orderlineService;

    public OrderlineController(OrderlineService orderlineService) {
        this.orderlineService = orderlineService;
    }

    @GetMapping
    public ResponseEntity<List<OrderlineDto>> getAllOrderline() {
        return ResponseEntity.ok().body(orderlineService.getAllOrderline());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderlineDto> getOrderline(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderlineService.getOrderline(id));
    }

    @PostMapping
    public ResponseEntity<Object> addOrderline(@Valid @RequestBody OrderlineDto orderlineDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            OrderlineDto addedOrderline = orderlineService.addOrderline(orderlineDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedOrderline.id)));
            return ResponseEntity.created(uri).body(addedOrderline);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrderline(@PathVariable Long id, @RequestBody OrderlineDto orderlineDto) {
        OrderlineDto updateOrderline = orderlineService.updateOrderline(id, orderlineDto);
        return ResponseEntity.ok().body(updateOrderline);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrderline(@PathVariable Long id) {
        orderlineService.deleteOrderline(id);
        return ResponseEntity.noContent().build();
    }

}