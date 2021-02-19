package ru.vyazankin.market.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.vyazankin.market.dto.OrderDto;
import ru.vyazankin.market.exceptions.ResourceNotFoundException;
import ru.vyazankin.market.model.User;
import ru.vyazankin.market.service.OrderService;
import ru.vyazankin.market.service.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders(Principal principal){
        if (principal == null) return Collections.emptyList();
        User user = userService.findByUsername(principal.getName())
                .orElseThrow( ()-> new ResourceNotFoundException("Не удалось найти пользоватетя с именем " + principal.getName()));
        return orderService.orderParamDtoList(user);
    }

    @PostMapping("/makeorder")
    public void makeOrder(@RequestParam(name = "deliveryAddress") String deliveryAddress, Principal principal){
        //т.к. персональных скидок у нас нет, Principal нам явно пригождается первый раз при заказе
        User user = userService.findByUsername(principal.getName())
                .orElseThrow( ()-> new ResourceNotFoundException("Не удалось найти пользоватетя с именем " + principal.getName()));
        orderService.saveOrderFromUserCart(user, deliveryAddress);
    }

}
