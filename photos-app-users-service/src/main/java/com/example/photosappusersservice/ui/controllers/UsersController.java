package com.example.photosappusersservice.ui.controllers;

import com.example.photosappusersservice.data.UserEntity;
import com.example.photosappusersservice.service.UsersServiceImpl;
import com.example.photosappusersservice.shared.UserDto;
import com.example.photosappusersservice.ui.model.AlbumResponseModel;
import com.example.photosappusersservice.ui.model.CreateUserRequestModel;
import com.example.photosappusersservice.ui.model.CreateUserResponseModel;
import com.example.photosappusersservice.ui.model.UserResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {

    private final Environment environment;
    private final UsersServiceImpl usersService;
    private final RestTemplate restTemplate;

    @GetMapping("/status")
    public String getUsers() {
        return "working on port " + environment.getProperty("local.server.port") + "\n Secret: "+ environment.getProperty("token.secret");
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
        log.info("User creation requested");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = usersService.createUser(userDto);
        CreateUserResponseModel response = modelMapper.map(createdUser, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {
        log.info("Retrieve single user requested");
        UserDto userDto = usersService.getUserByUserId(userId);
        UserResponseModel userResponseModel = new ModelMapper().map(userDto, UserResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
    }
}
