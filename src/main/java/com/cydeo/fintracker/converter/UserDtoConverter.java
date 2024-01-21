package com.cydeo.fintracker.converter;

import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.service.UserService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserDtoConverter implements Converter<String, UserDto> {

    private final UserService userService;

    public UserDtoConverter(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }

        return userService.findUserById(Long.parseLong(source));

    }

}
