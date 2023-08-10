package com.experlabs.SpringBootStart.user.respository;

import com.experlabs.SpringBootStart.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user where user.id=?1")
    Optional<User> findUserByID(Long id);

    @Query("select user from User user where user.email=?1")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT user FROM User user WHERE LOWER(user.name) LIKE LOWER(concat('%', ?1, '%'))")
    List<User> findUserByNameFuzzySearch(String name);
}
