package com.epam.hibernate.security;

import com.epam.hibernate.entity.User;
import com.epam.hibernate.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ApplicationUserService implements UserDetailsService {
    private final UserRepository userRepository;

    public ApplicationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;

        user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        if (user != null) {
            return new ApplicationUser(
                    user.getUserId(), user.getUsername(), user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );
        }
        throw new UsernameNotFoundException(String.format("Username %s not found", username));
    }
}
