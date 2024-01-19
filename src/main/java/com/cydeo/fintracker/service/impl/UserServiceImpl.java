package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.User;
import com.cydeo.fintracker.repository.UserRepository;
import com.cydeo.fintracker.service.UserService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;


    @Override
    public UserDto findUserById(Long id) {
        return null;
    }

    @Override
    public UserDto findByUsername(String username) {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NoSuchElementException("There is no user with given username");
        }
        return mapperUtil.convert(user, new UserDto());
    }
}
