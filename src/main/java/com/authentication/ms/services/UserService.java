package com.authentication.ms.services;

import com.authentication.ms.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO, String username);
}