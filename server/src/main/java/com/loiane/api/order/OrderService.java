package com.loiane.api.order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.loiane.api.user.User;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order placeOrder(OrderDTO orderDTO, User user) {
        Order order = new Order();
        order.setDescription(orderDTO.description());
        order.setUser(user);
        return orderRepository.save(order);
    }

}
