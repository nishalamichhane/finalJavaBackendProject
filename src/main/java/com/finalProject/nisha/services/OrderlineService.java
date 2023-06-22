package com.finalProject.nisha.services;

import com.finalProject.nisha.dtos.OrderlineDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.models.Orderline;
import com.finalProject.nisha.repositories.OrderlineRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class OrderlineService {
    private final OrderlineRepository orderlineRepository;

    public OrderlineService(OrderlineRepository orderlineRepository) {
        this.orderlineRepository = orderlineRepository;
    }

    public List<OrderlineDto> getAllOrderline() {
        Iterable<Orderline> orderlines = orderlineRepository.findAll();
        List<OrderlineDto> orderlineDtos = new ArrayList<>();

        for (Orderline o: orderlines) {
            orderlineDtos.add(transferOrderlineToDto(o));
        }

        return orderlineDtos;
    }

    public OrderlineDto getOrderline(Long id) {
        Optional<Orderline> orderlineOptional = orderlineRepository.findById(id);

        if(orderlineOptional.isEmpty()) {
            throw new RecordNotFoundException("Product didn't find with this id: " + id);
        }

        Orderline orderline = orderlineOptional.get();

        return transferOrderlineToDto(orderline);
    }

    public OrderlineDto addOrderline(OrderlineDto orderlineDto) {
        Orderline orderline = transferDtoToOrderline(orderlineDto);
        orderlineRepository.save(orderline);

        return transferOrderlineToDto(orderline);
    }

    public OrderlineDto updateOrderline(Long id, OrderlineDto orderlineDto) {
        Optional<Orderline> orderlineOptional = orderlineRepository.findById(id);
        if(orderlineOptional.isEmpty()) {
            throw new RecordNotFoundException("Orderline didn't find with this id: " + id);
        }

        Orderline updateOrderline = transferDtoToOrderline(orderlineDto);
        updateOrderline.setId(id);
        orderlineRepository.save(updateOrderline);

        return transferOrderlineToDto(updateOrderline);
    }

    public void deleteOrderline(Long id) {
        Optional<Orderline> optionalOrderline = orderlineRepository.findById(id);
        if(optionalOrderline.isEmpty()) {
            throw new RecordNotFoundException("Orderline didn't find with this id: " + id);
        }
        orderlineRepository.deleteById(id);
    }

    public OrderlineDto transferOrderlineToDto(Orderline orderline) {
        OrderlineDto orderlineDto = new OrderlineDto();

        orderlineDto.id = orderline.getId();
        orderlineDto.quantity = orderline.getQuantity();
        orderlineDto.subTotal = orderline.getSubTotal();
        orderlineDto.product = orderline.getProduct();
        orderlineDto.order = orderline.getOrder();
        return orderlineDto;
    }

    public Orderline transferDtoToOrderline(OrderlineDto orderlineDto) {
        Orderline orderline = new Orderline();

        // we don't need setId , that generates in the database or will be in de URL
        orderline.setQuantity(orderlineDto.quantity);
        orderline.setSubTotal(orderlineDto.subTotal);
        orderline.setProduct(orderlineDto.product);
        orderline.setOrder(orderlineDto.order);
        return orderline;
    }
}