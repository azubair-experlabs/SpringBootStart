package com.experlabs.SpringBootStart.user.service;

import com.experlabs.SpringBootStart.core.utils.StringUtil;
import com.experlabs.SpringBootStart.core.utils.ValidationUtil;
import com.experlabs.SpringBootStart.user.dto.MetaData;
import com.experlabs.SpringBootStart.user.dto.UsersDto;
import com.experlabs.SpringBootStart.user.models.Address;
import com.experlabs.SpringBootStart.user.models.User;
import com.experlabs.SpringBootStart.user.respository.AddressRepository;
import com.experlabs.SpringBootStart.user.respository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final AddressRepository addressRepo;

    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers(Map<String, String> queryParams) {
        String name = queryParams.get("name");
        if (name != null && !name.isEmpty())
            return userRepo.findUserByNameFuzzySearch(name);
        return userRepo.findAll();
    }
  public UsersDto getUsers(Map<String, String> queryParams) {
    String name = queryParams.get("name");
    String pageSizeParam = queryParams.get("pageSize");
    String pageNumberParam = queryParams.get("pageNumber");
    int pageSize = 10;
    int pageNumber = 0;
    if (!StringUtil.isNullOrEmpty(pageSizeParam) && !StringUtil.isNullOrEmpty(pageNumberParam)) {
      pageSize = Integer.parseInt(pageSizeParam);
    }

    if (!StringUtil.isNullOrEmpty(pageSizeParam) && !StringUtil.isNullOrEmpty(pageNumberParam)) {
      pageNumber = Integer.parseInt(pageNumberParam);
    }

    Pageable pageable = PageRequest.of(pageNumber, pageSize);

    Page<User> page;
    if (!StringUtil.isNullOrEmpty(name)) {
      page = userRepo.findUserByNameFuzzySearch(name, pageable);
    } else {
      page = userRepo.findAll(pageable);
    }
    List<User> data = page.getContent();

    MetaData metadata = MetaData.builder().totalItems(page.getTotalElements())
        .pageSize((long) pageSize)
        .pageNumber((long) pageNumber).hasMore(page.hasNext()).build();

    return new UsersDto(metadata, data);
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

        if (!StringUtil.isNullOrEmpty(name) && !name.equals(user.getName()))
            user.setName(name);
        if (!StringUtil.isNullOrEmpty(password) && !passwordEncoder.matches(password, user.getPassword()))
            user.setPassword(passwordEncoder.encode(password));
        if (!StringUtil.isNullOrEmpty(email) && !email.equals(user.getName()) && ValidationUtil.isValidEmail(email)) {
            boolean isAlreadyTaken = userRepo.findUserByEmail(email).isPresent();
            if (isAlreadyTaken)
                throw new IllegalStateException("email already taken!");
            else
                user.setEmail(email);
        }
        return user;
    }

    @Override
    @Transactional
    public Address updateUserAddressById(Long id, Address address) {
        boolean notValid = StringUtil.isNullOrEmpty(address.getStreetAddress())
                || StringUtil.isNullOrEmpty(address.getCity())
                || StringUtil.isNullOrEmpty(address.getCountry())
                || address.getType() == null;
        if (notValid)
            throw new IllegalArgumentException("Address not valid!");
        User user = userRepo.findUserByID(id).orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));

        address.setUser(user);
        user.getAddresses().add(address);
        userRepo.save(user);
        return address;
    }

    @Override
    public List<Address> getUserAddressById(Long id) {
        Optional<List<Address>> addresses = addressRepo.findByUser_Id(id);
                return addresses.orElseThrow(() -> new EntityNotFoundException(String.format("user not found with id %d", id)));
    }
}
