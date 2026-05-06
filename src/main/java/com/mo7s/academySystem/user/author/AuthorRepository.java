package com.mo7s.academySystem.user.author;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author , Integer> {

    Optional<Author> findByUserName(String userName);
    Optional<Author> findByEmail(String email);
}
