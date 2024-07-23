package com.haptm.todoapp.controller;

import com.haptm.todoapp.dto.PasswordDto;
import com.haptm.todoapp.dto.SignUpDto;
import com.haptm.todoapp.dto.UserDto;
import com.haptm.todoapp.model.User;
import com.haptm.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(
        name = "CRUD REST APIs for User Resource",
        description = "CRUD REST APIs - Create User, Update User, Get User, Get All Users, Delete User"
)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    @Operation(
            summary = "Create User REST API",
            description = "Create User REST API is used to save user in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PostMapping("/create-user")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpDto signUpDto) {
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setPasswordHash(passwordEncoder.encode(signUpDto.getPassword()));
        user.setEmail(signUpDto.getEmail());
        user.userDefault();
        user.setTimestamp(LocalDateTime.now());
        userService.createUser(user);
        return ResponseEntity.ok().body("User registered successfully");
    }


    @Operation(
            summary = "Get User By ID REST API",
            description = "Get User By ID REST API is used to get a single user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@Valid @PathVariable Long id) {
        User user = userService.findById(id);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }


    @Operation(
            summary = "Get User By Username REST API",
            description = "Get User By Username REST API is used to get a single user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@Valid @PathVariable String username) {
        User user = userService.findByUsername(username);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }


    @Operation(
            summary = "Get User By Email REST API",
            description = "Get User By Email REST API is used to get a single user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail(@Valid @RequestBody String email) {
        User user = userService.findByEmail(email);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Update User REST API",
            description = "Update User REST API is used to update a particular user in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/{id}/information")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        UserDto userResponse = modelMapper.map(userService.updateUser(id, user), UserDto.class);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(
            summary = "Update User REST API",
            description = "Update User REST API is used to update user's password in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/{id}/password")
    public ResponseEntity<UserDto> changePassword(@Valid @PathVariable Long id, @Valid @RequestBody PasswordDto passwordDto) {
        User user = userService.updatePassword(id, passwordEncoder.encode(passwordDto.getCurrentPassword()), passwordEncoder.encode(passwordDto.getNewPassword()));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Delete User REST API",
            description = "Delete User REST API is used to delete a particular user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@Valid @PathVariable Long id, @Valid @RequestBody String password) {
        userService.deleteUser(id, passwordEncoder.encode(password));
        return ResponseEntity.ok().body("User deleted successfully");
    }
}
