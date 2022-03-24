package com.epam.esm.controller;

import com.epam.esm.UserService;
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

    @GetMapping
    @ResponseStatus(OK)
    public List<UserClientModel> findAll(@RequestParam Map<String, String> parameters) {
        return userService.readAll(parameters);
    }

    @GetMapping("/search")
    @ResponseStatus(OK)
    public List<UserClientModel> search(@RequestParam Map<String, String> parameters) {
        return userService.readByParameters(parameters);
    }



    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
