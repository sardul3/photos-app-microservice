package com.example.photosappusersservice.service;

import com.example.photosappusersservice.data.AlbumsServiceClient;
import com.example.photosappusersservice.data.UserEntity;
import com.example.photosappusersservice.data.UsersRepository;
import com.example.photosappusersservice.shared.UserDto;
import com.example.photosappusersservice.ui.model.AlbumResponseModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AlbumsServiceClient albumsServiceClient;

    @Override
    public UserDto createUser(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

        usersRepository.save(userEntity);

        UserDto response = modelMapper.map(userEntity, UserDto.class);
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(username);

        if(userEntity == null) throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);


        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = usersRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(userId);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<AlbumResponseModel> albumList = albumsServiceClient.getAlbums(userId);

        userDto.setAlbums(albumList);

        return userDto;
    }

}
