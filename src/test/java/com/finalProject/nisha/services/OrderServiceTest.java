package com.finalProject.nisha.services;

import com.finalProject.nisha.dtos.OrderDto;
import com.finalProject.nisha.dtos.ProductDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.models.Order;
import com.finalProject.nisha.models.Product;
import com.finalProject.nisha.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {OrderService.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Captor
    private ArgumentCaptor<Order> orderCaptor;
    @InjectMocks
    private OrderService orderService;
    private Order order1;
    OrderDto orderDto;

    @BeforeEach
    void setUp() {
        orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setTotalAmount(5000.0);
        orderDto.setUser(null);
    }
    @Test
    void getAllOrder() {
        // Arrange
        ArrayList<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(5000.0);
        orderList.add(order);
        when(orderRepository.findAll()).thenReturn(orderList);

        // Act
        List<OrderDto> actualAllOrder = orderService.getAllOrder();

        // Assert
        assertEquals(1, actualAllOrder.size());
        OrderDto getResult = actualAllOrder.get(0);
        assertEquals(order.getId(), getResult.getId());
        assertEquals(order.getTotalAmount(), getResult.getTotalAmount());
        verify(orderRepository).findAll();
    }
    @Test
    void getOrder() {
        // Arrange
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(5000.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        // Act
        OrderDto actualOrderId = orderService.getOrder(1L);
        // Assert
        assertEquals(1, actualOrderId.getId());
        assertEquals(5000.0, actualOrderId.getTotalAmount());
        verify(orderRepository).findById(1L);
    }
    @Test
    void updateOrder() {
        Long oid = 1L;
        Order exOrder = new Order();
        exOrder.setId(1L);
        exOrder.setTotalAmount(5000.0);

        OrderDto newOrderData = new OrderDto();
        newOrderData.setId(1L);
        newOrderData.setTotalAmount(5000.0);

        when(orderRepository.findById(oid)).thenReturn(Optional.of(exOrder));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OrderDto updatedOrder = orderService.updateOrder(oid, newOrderData);

        // Assert
        assertEquals(newOrderData.getId(), updatedOrder.getId());
        assertEquals(newOrderData.getTotalAmount(), updatedOrder.getTotalAmount());


        verify(orderRepository).findById(oid);
        verify(orderRepository).save(orderCaptor.capture());

        Order savedOrder = orderCaptor.getValue();
        assertEquals(newOrderData.getId(), savedOrder.getId());
        assertEquals(newOrderData.getTotalAmount(), savedOrder.getTotalAmount());
    }
    @Test
    void transferOrderToDto() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setTotalAmount(5000.0);
        OrderDto actualOrderToDtoResult = orderService.transferOrderToDto(order1);

        // Assert
        assertEquals(1L, actualOrderToDtoResult.getId());
        assertEquals(5000.0, actualOrderToDtoResult.getTotalAmount());
    }
    @Test
    void transferDtoToOrder() {
        // Arrange
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setTotalAmount(5000.0);

        // Act
        Order actualTransferDtoToOrderResult = orderService.transferDtoToOrder(orderDto);

        // Assert
        assertEquals(1L, actualTransferDtoToOrderResult.getId());
        assertEquals(5000.0, actualTransferDtoToOrderResult.getTotalAmount());
    }

}