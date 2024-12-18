package com.epam.vital.gym_crm.domain.repository;

import com.epam.vital.gym_crm.domain.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);
}
