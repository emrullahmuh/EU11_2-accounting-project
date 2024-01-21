package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.User;
import com.cydeo.fintracker.exception.UserNotFoundException;
import com.cydeo.fintracker.repository.UserRepository;
import com.cydeo.fintracker.service.UserService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;


    @Override
    public UserDto findUserById(Long id) {

        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found."));

        log.info(String.valueOf(user));

        return mapperUtil.convert(user,new UserDto());
    }

    @Override
    public UserDto findByUsername(String username) {

        User user = userRepository.findByUsername(username).get();
        if (user == null) {
            throw new NoSuchElementException("There is no user with given username");
        }
        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public List<UserDto> listAllUsers() {

        List<User> userList = userRepository.findAllByIsDeleted(false);

        return userList.stream().map(user -> mapperUtil.convert(user,new UserDto())).collect(Collectors.toList());
    }

    @Override
    public void save(UserDto user) {
        userRepository.save(mapperUtil.convert(user, new User()));
    }

    @Override
    public UserDto update(UserDto user) {
        User storedUser = userRepository.findByUsername(user.getUsername()).get();

        User convertedUser = mapperUtil.convert(user, new User());

        convertedUser.setId(storedUser.getId());

        userRepository.save(convertedUser);

        return findByUsername(user.getUsername());
    }

    @Override
    public void delete(Long userId) {
        User user = userRepository.findById(userId).get();

        user.setIsDeleted(true);

        userRepository.save(user);
    }


}
