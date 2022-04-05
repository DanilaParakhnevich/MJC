package com.epam.esm.controller;

import com.epam.esm.OrderService;
import com.epam.esm.UserService;
import com.epam.esm.dto.OrderClientModel;
import com.epam.esm.dto.UserClientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
    OrderService orderService;

    @GetMapping
    @ResponseStatus(OK)
    public List<UserClientModel> findAll(@RequestParam Map<String, String> parameters) {
        return userService.readAll(parameters);
    }

    @GetMapping("/orders")
    @ResponseStatus(OK)
    public List<OrderClientModel> readOrdersByUserId(@RequestParam Map<String, String> parameters) {
        return orderService.readByUserId(parameters);
    }


    @GetMapping("/search")
    @ResponseStatus(OK)
    public List<UserClientModel> search(@RequestParam Map<String, String> parameters) {
        return userService.readByParameters(parameters);
    }


    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
