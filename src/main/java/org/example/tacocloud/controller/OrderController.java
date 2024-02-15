package org.example.tacocloud.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.tacocloud.config.OrderProps;
import org.example.tacocloud.data.OrderRepository;
import org.example.tacocloud.data.UserRepository;
import org.example.tacocloud.entity.TacoOrder;
import org.example.tacocloud.entity.User;

import org.example.tacocloud.messaging.OrderMessagingService;
import org.example.tacocloud.messaging.RabbitOrderMessagingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderRepository orderRepo;
    private UserRepository userRepo;
    private OrderProps orderProps;
    private RabbitOrderMessagingService messageService;
    public OrderController(OrderRepository orderRepo, UserRepository userRepo, OrderProps orderProps, RabbitOrderMessagingService messageService) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.orderProps = orderProps;
        this.messageService = messageService;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("tacoOrder", new TacoOrder());
        return "orderForm";
    }
    @PostMapping
    public String processOrder(@Valid @ModelAttribute("tacoOrder") TacoOrder order, Errors errors,
                               SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if(errors.hasErrors()) {
            return "orderForm";
        }
        order.setUser(user);
        messageService.sendOrder(order);
        orderRepo.save(order);
        sessionStatus.setComplete();
        log.info("Order submitted: " + order);
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(
            @AuthenticationPrincipal User user, Model model) {

        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }
}
