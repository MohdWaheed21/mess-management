package com.hostel.mess.repository;

import com.hostel.mess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRollNumber(String rollNumber);
    boolean existsByEmail(String email);
    boolean existsByRollNumber(String rollNumber);
    List<User> findByActiveTrue();
}
