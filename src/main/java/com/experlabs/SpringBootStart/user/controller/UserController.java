package com.experlabs.SpringBootStart.user.controller;

import com.experlabs.SpringBootStart.core.models.ResponseBody;
import com.experlabs.SpringBootStart.user.dto.UsersDto;
import com.experlabs.SpringBootStart.user.models.Address;
import com.experlabs.SpringBootStart.user.models.User;
import com.experlabs.SpringBootStart.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
  public UsersDto getUsers(@RequestParam Map<String, String> queryParams) {
    return userService.getUsers(queryParams);
  }

  @GetMapping(path = "{userId}")
  public ResponseEntity<Object> getUser(@PathVariable("userId") Long id) {
    try {
      User user = userService.getUserByID(id);
      return ResponseEntity.ok(user);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
  }

  @DeleteMapping(path = "{userId}")
  public ResponseEntity<Object> deleteUser(@PathVariable("userId") Long id) {
    try {
      userService.deleteUserByID(id);
      return ResponseEntity.ok(new ResponseBody(HttpStatus.OK.value(),
          String.format("user id %d deleted successfully!", id)));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
  }

  @PutMapping(path = "{userId}")
  public ResponseEntity<Object> updateUserData(
      @PathVariable("userId") Long id,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String password
  ) {
    try {
      User user = userService.updateUserByID(id, name, email, password);
      return ResponseEntity.ok(user);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
  }

  @GetMapping(path = "/address/{userId}")
  public ResponseEntity<Object> addUserAddress(@PathVariable() Long userId) {
    try {
      List<Address> result = userService.getUserAddressById(userId);
      return ResponseEntity.ok(result);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ResponseBody(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
  }

  @GetMapping(path = "/address")
  public ResponseEntity<Object> addUserAddress(HttpServletRequest request) {
    try {
      User userDetails = (User) request.getAttribute("userDetails");
      List<Address> result = userService.getUserAddressById(userDetails.getId());
      return ResponseEntity.ok(result);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ResponseBody(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
  }

  @PostMapping(path = "/address")
  public ResponseEntity<Object> addUserAddress(@RequestBody(required = false) Address address,
      HttpServletRequest request) {
    try {
      User userDetails = (User) request.getAttribute("userDetails");
      Address result = userService.updateUserAddressById(userDetails.getId(), address);
      return ResponseEntity.ok(result);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ResponseBody(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
  }
}
