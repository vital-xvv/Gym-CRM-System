package com.epam.vital.gym_crm.domain.service;

import com.epam.vital.gym_crm.domain.model.User;
import com.epam.vital.gym_crm.domain.model.UserPrincipal;
import com.epam.vital.gym_crm.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) throw new UsernameNotFoundException("User with username: %s is not found!".formatted(username));
        return new UserPrincipal(user.get());
    }
}
