package com.project.shopapp.configurations;

import com.project.shopapp.common.enums.ERole;
import com.project.shopapp.models.Account;
import com.project.shopapp.models.Role;
import com.project.shopapp.repositories.AccountRepository;
import com.project.shopapp.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";
    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(AccountRepository accountRepository, RoleRepository roleRepository) {
        log.info("Init application ...");
        return args -> {
            if (accountRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {

                Role adminRole = roleRepository.save(new Role(ERole.ADMIN));

                Account account = new Account();
                account.setUsername(ADMIN_USER_NAME);
                account.setPasswordHash(passwordEncoder.encode(ADMIN_PASSWORD));
                account.addRole(adminRole);

                accountRepository.save(account);
                log.info("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
