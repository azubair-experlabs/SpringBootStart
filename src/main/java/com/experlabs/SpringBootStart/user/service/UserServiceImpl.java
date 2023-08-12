package com.experlabs.SpringBootStart.user.service;

import com.experlabs.SpringBootStart.core.utils.HelperMethods;
import com.experlabs.SpringBootStart.user.models.User;
import com.experlabs.SpringBootStart.user.respository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUsers(Map<String, String> queryParams) {
        String name = queryParams.get("name");
        if (name != null && !name.isEmpty())
            return userRepo.findUserByNameFuzzySearch(name);
        return userRepo.findAll();
    }

    public User getUserByID(Long id) {
        Optional<User> response = userRepo.findUserByID(id);
        return response.orElseThrow(() -> new EntityNotFoundException(String.format("user not found with id %d", id)));
    }

    public void deleteUserByID(Long id) {
        Optional<User> response = userRepo.findUserByID(id);
        if (response.isPresent()) {
            userRepo.delete(response.get());
        }
        else
            throw new EntityNotFoundException(String.format("user with id %d does not exists",id));
    }

    @Override
    @Transactional
    public User updateUserByID(Long id, String name, String email, String password) {
        User user = userRepo.findUserByID(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("user not found with id %d", id)));

        if (name != null && !name.isBlank() && !name.equals(user.getName()))
            user.setName(name);
        if (password != null && !password.isBlank() && !password.equals(user.getPassword()))
            user.setPassword(password);
        if (email != null && !email.isBlank() && !email.equals(user.getName()) && HelperMethods.isValidEmail(email)) {
            boolean isAlreadyTaken = userRepo.findUserByEmail(email).isPresent();
            if (isAlreadyTaken)
                throw new IllegalStateException("email already taken!");
            else
                user.setEmail(email);
        }
        return user;
    }
}
