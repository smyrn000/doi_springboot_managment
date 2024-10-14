package com.doi.business.services;

import com.doi.business.model.LoginDTO;
import com.doi.business.model.RegistrationDTO;
import com.doi.business.model.Role;
import com.doi.business.model.User;
import com.doi.business.repository.RoleRepository;
import com.doi.business.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
     private UserRepository userRepository;
    @Autowired
     private RoleRepository roleRepository;
    @Autowired
     private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;


    /* Registers a new user or admin. */
    public User registerUser(String username, String password, String full_name, int afm, String email, String address, String phone_num)
    {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return  userRepository.save(new User(username,
                encodedPassword,
                userRole,
                full_name,
                email,
                address,
                phone_num));
    }
    public User registerEmployee(String username, String password, String full_name, int afm, String email, String address, String phone_num){
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return  userRepository.save(new User(username,
                encodedPassword,
                userRole,
                full_name,
                email,
                address,
                phone_num));
    }
    public User registerAdmin(String username, String password)
    {
        String encodedPassword = passwordEncoder.encode(password);
        Role adminRole = roleRepository.findByAuthority("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return userRepository.save(new User(
                username,
                encodedPassword,
                adminRole
        ));
    }

    /* Authenticates a user and generates a JWT. */
    public LoginDTO loginUser(String username, String password){
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );
            String token = tokenService.generateJwt(auth);
            return new LoginDTO(userRepository.findByUsername(username).get(), token);
        }catch (AuthenticationException e){
            return new LoginDTO(null, "");
        }
    }
    /* Updates user details. */
    public User updateUser(String username, RegistrationDTO userDTO) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (!existingUser.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User user = existingUser.get();
        user.setFull_name(userDTO.getFull_name());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setPhone_num(userDTO.getPhone_num());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return userRepository.save(user);
    }
    /* Deletes a user. */
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
