package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.User;
import com.cydeo.fintracker.entity.common.UserPrincipal;
import com.cydeo.fintracker.repository.UserRepository;
import com.cydeo.fintracker.service.SecurityService;
import com.cydeo.fintracker.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final UserService userService;

    public SecurityServiceImpl(UserRepository userRepository, @Lazy UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }
    @Override
    public UserDto getLoggedInUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
}
