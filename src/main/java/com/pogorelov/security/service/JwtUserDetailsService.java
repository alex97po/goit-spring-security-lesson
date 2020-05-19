package com.pogorelov.security.service;

import com.pogorelov.security.config.UserDetailsImpl;
import com.pogorelov.security.domain.User;
import com.pogorelov.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("No user found for username="+username));
        log.info("USER FOUND BY USERNAME: {}", user);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        log.info("USER ROLE IS : {}", userDetails.getAuthorities());
        return userDetails;
    }
}
