package com.authentication.ms.controllers;
import com.authentication.ms.dto.AuthResponseDTO;
import com.authentication.ms.dto.UserDTO;
import com.authentication.ms.models.UserEntity;
import com.authentication.ms.repository.UserRepository;
import com.authentication.ms.security.JWTGenerator;
import com.authentication.ms.services.UserService;
import com.authentication.ms.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private JWTGenerator jwtGenerator;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
    }

    // Create user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO){
        if(userRepository.existsByUsername(userDTO.getUsername())){
            return new ResponseEntity<>("Error: usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setUserId(userDTO.getUserId());

        userRepository.save(userEntity);

        return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
    }

    // Update user
    @PutMapping("/update/{username}")
    public ResponseEntity<String> update(@RequestBody UserDTO userDTO, @PathVariable String username){
        if(!userRepository.existsByUsername(username)){
            return new ResponseEntity<>("Error: usuario no existe", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userRepository.findByUsername(username);

        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(userEntity);

        return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
    }

    // Delete user
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable int userId){
        if(!userRepository.existsByUserId(userId)){
            return new ResponseEntity<>("Error: usuario no existe", HttpStatus.BAD_REQUEST);
        }

        userRepository.deleteByUserId(userId);

        return new ResponseEntity<>("Borrado exitoso", HttpStatus.OK);
    }

    // Authenticate user
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserDTO userDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
}