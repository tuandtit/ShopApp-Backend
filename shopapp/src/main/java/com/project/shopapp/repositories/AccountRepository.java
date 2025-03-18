package com.project.shopapp.repositories;

import com.project.shopapp.common.enums.AuthProvider;
import com.project.shopapp.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<Account> findByUsernameAndAuthProvider(String username, AuthProvider authProvider);
}