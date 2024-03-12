package com.authentication.ms.services.impl;

import com.authentication.ms.dto.UserDTO;
import com.authentication.ms.models.UserEntity;
import com.authentication.ms.repository.UserRepository;
import com.authentication.ms.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Already implemented in controller
    @Override
    public UserDTO createUser(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());

        UserEntity newUserEntity = userRepository.save(userEntity);

        UserDTO userResponse = new UserDTO();
        userResponse.setUsername(newUserEntity.getUsername());
        userResponse.setPassword(newUserEntity.getPassword());

        return userResponse;
    }

    // Already implemented in controller
    @Override
    public UserDTO updateUser(UserDTO userDTO, String username){
        // Must handle exception
        UserEntity userEntity = userRepository.findByUsername(username);

        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userDTO;
    }
}