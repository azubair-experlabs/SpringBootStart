package com.experlabs.SpringBootStart.user.respository;

import com.experlabs.SpringBootStart.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user left join fetch user.addresses where user.id=?1")
    Optional<User> findUserByID(Long id);

    @Query("SELECT user FROM User user WHERE user.email = :email")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT user FROM User user WHERE LOWER(user.name) LIKE LOWER(concat('%', ?1, '%'))")
    Page<User> findUserByNameFuzzySearch(String name, Pageable pageable);
}
