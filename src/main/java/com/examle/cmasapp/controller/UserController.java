package com.examle.cmasapp.controller;

import com.examle.cmasapp.dto.UserDto;
import com.examle.cmasapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@AllArgsConstructor
@RestController
public class UserController {

    private final UserService service;

    @GetMapping
    public Iterable<UserDto> getUsers() {
        return service.findAllUsers();
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserById(@PathVariable("id") int id) {
        UserDto userDto = service.findUserById(id);
        return EntityModel.of(userDto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUsers()).withRel("users"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") int id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUserDto = service.createUser(userDto);
        return ResponseEntity.ok(createdUserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") int id, @RequestBody UserDto userDto) {
        UserDto updatedUserDto = service.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUserDto);
    }
}
