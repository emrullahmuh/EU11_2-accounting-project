package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.User;
import com.cydeo.fintracker.repository.UserRepository;
import com.cydeo.fintracker.service.UserService;
import com.cydeo.fintracker.util.MapperUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    public UserDto findByUsername(String username) {
        User user =userRepository.findByUsername (username);
        return mapperUtil.convert(user, new UserDto());
    }
}
