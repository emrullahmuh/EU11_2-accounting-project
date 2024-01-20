package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {

        UserDto getLoggedInUser();


}
