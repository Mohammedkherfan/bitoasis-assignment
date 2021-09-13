package com.bitoasis.assignment.security;

import com.bitoasis.assignment.bo.UserBo;
import com.bitoasis.assignment.service.FindUserByEmailService;
import com.bitoasis.assignment.util.Encryption;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsSecurityService implements UserDetailsService {

    private FindUserByEmailService findUserByEmailService;

    public UserDetailsSecurityService(FindUserByEmailService findUserByEmailService) {
        this.findUserByEmailService = findUserByEmailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails user = null;
        Optional<UserBo> userBo = findUserByEmailService.findByEmail(email);
        if (userBo.isPresent()) {
            user = User.builder()
                    .username(userBo.get().getEmail())
                    .password(Encryption.decrypt(userBo.get().getPassword()))
                    .roles("USER")
                    .build();
        }
        return user;
    }
}
