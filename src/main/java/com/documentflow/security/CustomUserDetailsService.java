package com.documentflow.security;


import com.documentflow.entity.User;
import com.documentflow.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;


// bridge between security and user
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return mapToUserDetails(user);
    }

    // spring sec. works only UserDetails
    // which contains the exact security info (username, password, roles)

    private UserDetails mapToUserDetails(User user){

        UserBuilder builder = org.springframework.security.core.userdetails.User // not user entity
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]));

        return builder.build();

    }
}
