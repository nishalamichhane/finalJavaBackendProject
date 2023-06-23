package com.finalProject.nisha.services;

import com.finalProject.nisha.dtos.OrderDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.models.Order;
import com.finalProject.nisha.repositories.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDto> getAllOrder() {
        Iterable<Order> order = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order ord: order) {
            orderDtos.add(transferOrderToDto(ord));
        }

        return orderDtos;
    }

    public OrderDto getOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if(orderOptional.isEmpty()) {
            throw new RecordNotFoundException("Order didn't find with this id: " + id);
        }

        Order order = orderOptional.get();

        return transferOrderToDto(order);
    }

    public OrderDto addOrder(OrderDto orderDto) {
        Order order = transferDtoToOrder(orderDto);
        orderRepository.save(order);

        return transferOrderToDto(order);
    }

    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty()) {
            throw new RecordNotFoundException("Order didn't find with this id: " + id);
        }

        Order updateOrder = transferDtoToOrder(orderDto);
        updateOrder.setId(id);
        orderRepository.save(updateOrder);

        return transferOrderToDto(updateOrder);
    }

    public void deleteOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isEmpty()) {
            throw new RecordNotFoundException("Order didn't find with this id: " + id);
        }
        orderRepository.deleteById(id);
    }

    public OrderDto transferOrderToDto(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.id = order.getId();
        orderDto.totalAmount = order.getTotalAmount();
        orderDto.userId = order.getUserId();
        orderDto.orderline = order.getOrderline();
        orderDto.invoice = order.getInvoice();
        return orderDto;
    }

    public Order transferDtoToOrder(OrderDto orderDto) {
        Order order = new Order();

        // we don't need setId , that generates in the database or will be in de URL
        order.setTotalAmount(orderDto.totalAmount);
        order.setUserId(orderDto.userId);
        order.setOrderline(orderDto.orderline);
        order.setInvoice(orderDto.invoice);
        return order;
    }
}
