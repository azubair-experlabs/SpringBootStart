package com.experlabs.SpringBootStart.user.respository;

import com.experlabs.SpringBootStart.user.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<List<Address>> findByUser_Id(Long user_id);
}