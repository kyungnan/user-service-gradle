package com.example.userservicegradle.controller;

import com.example.userservicegradle.dto.UserDto;
import com.example.userservicegradle.service.UserService;
import com.example.userservicegradle.vo.Greeting;
import com.example.userservicegradle.vo.RequestUser;
import com.example.userservicegradle.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private Environment env;
    private UserService userService;
    @Autowired
    private Greeting greeting;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health-check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
    //    return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        // RequestUser -> UserDto
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);

        // UserService에서 create
        userService.createUser(userDto);

        // UserDto -> ResponseUser
        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

    //    return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
