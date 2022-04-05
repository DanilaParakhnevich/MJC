package com.epam.esm.controller;

import com.epam.esm.OrderService;
import com.epam.esm.dto.OrderClientModel;
import com.epam.esm.handler.RequestParameter;
import com.epam.esm.handler.exception.BadParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/orders")
public class OrderController {
    OrderService orderService;

    @ResponseStatus(OK)
    @GetMapping("/search")
    public List<OrderClientModel> search(@RequestParam Map<String, String> parameters) {
        return orderService.search(parameters);
    }

    @ResponseStatus(OK)
    @GetMapping()
    public List<OrderClientModel> readAll(@RequestParam Map<String, String> parameters) {
        return orderService.readAll(parameters);
    }

    @ResponseStatus(CREATED)
    @PostMapping()
    public OrderClientModel create(@RequestBody OrderClientModel order,
                                   @RequestParam Map<String, String> parameters) {
        if (parameters.containsKey(RequestParameter.ID)) {
            return orderService.create(order,
                    Long.parseLong(parameters.remove(RequestParameter.ID)));
        }
        throw new BadParameterException("bad.param");
    }


    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
