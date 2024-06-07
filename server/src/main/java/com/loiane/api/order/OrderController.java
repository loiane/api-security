package com.loiane.api.order;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loiane.api.user.User;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Order> listOrdersByUserId(@RequestParam Long userId) {
        return orderService.findByUserId(userId);
    }

    @GetMapping("/id")
    @PreAuthorize("isAuthenticated()")
    public Order listOrderById(@RequestParam Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Order placeOrder(@RequestBody OrderDTO order) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return orderService.placeOrder(order, currentUser);
    }
}
