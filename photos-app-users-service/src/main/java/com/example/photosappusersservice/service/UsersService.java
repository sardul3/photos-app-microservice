package com.example.photosappusersservice.service;

import com.example.photosappusersservice.data.UserEntity;
import com.example.photosappusersservice.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UsersService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);

    UserDto getUserDetailsByEmail(String email);

    UserDto getUserByUserId(String userId);
}
