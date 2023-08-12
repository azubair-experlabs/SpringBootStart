package com.experlabs.SpringBootStart.user.controller;

import com.experlabs.SpringBootStart.user.models.User;
import com.experlabs.SpringBootStart.user.service.UserService;
import com.experlabs.SpringBootStart.core.models.ResponseBody;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers(@RequestParam Map<String, String> queryParams) {
        return userService.getUsers(queryParams);
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<Object> getUser(@PathVariable("userId") Long id) {
        try {
            User user = userService.getUserByID(id);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") Long id) {
        try {
            userService.deleteUserByID(id);
            return ResponseEntity.ok(new ResponseBody(HttpStatus.OK.value(), String.format("user id %d deleted successfully!", id)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<Object> updateUser(
            @PathVariable("userId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password) {
        try {
            User user = userService.updateUserByID(id, name, email, password);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
