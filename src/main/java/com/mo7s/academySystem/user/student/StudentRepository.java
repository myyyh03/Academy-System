package com.mo7s.academySystem.user.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student , Integer> {
    Optional<Student> findByUserName(String userName);
    Optional<Student> findByEmail(String email);
}
