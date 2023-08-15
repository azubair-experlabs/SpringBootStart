package com.experlabs.SpringBootStart.user.service;

import com.experlabs.SpringBootStart.user.models.Address;
import com.experlabs.SpringBootStart.user.models.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUsers(Map<String, String> queryParams);

    User getUserByID(Long id);

    void deleteUserByID(Long id);

    User updateUserByID(Long id, String name, String email, String password);

    Address updateUserAddressById(Long id, Address address);

    List<Address> getUserAddressById(Long id);
}
