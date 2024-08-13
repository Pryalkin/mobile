package com.pryalkin.Task.service.impl;

import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.request.UserRequestDTO;
import com.pryalkin.Task.exception.model.PasswordException;
import com.pryalkin.Task.exception.model.UsernameExistException;
import com.pryalkin.Task.model.Server;
import com.pryalkin.Task.model.ServerPrincipal;
import com.pryalkin.Task.model.User;
import com.pryalkin.Task.model.UserPrincipal;
import com.pryalkin.Task.repository.ServerRepository;
import com.pryalkin.Task.repository.UserRepository;
import com.pryalkin.Task.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.pryalkin.Task.constant.UserImplConstant.*;
import static com.pryalkin.Task.enumeration.Role.ROLE_ADMIN;
import static com.pryalkin.Task.enumeration.Role.ROLE_USER;

@Service
@Qualifier("userDetailsService")
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final ServerRepository serverRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        if(username.length() > "server".length() && username.substring(0, "server".length()).equals("server")){
            System.out.println("TYT TYT TYT");
            Server server = null;
            try {
                server = serverRepository.findByServerName(username)
                        .orElseThrow(() -> new UsernameExistException(USERNAME_ALREADY_EXISTS));
            } catch (UsernameExistException e) {
                throw new RuntimeException(e);
            }
            ServerPrincipal serverPrincipal = new ServerPrincipal(server);
            return serverPrincipal;
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username));
        UserPrincipal userPrincipal = new UserPrincipal(user);
        log.info(FOUND_USER_BY_USERNAME + username);
        return userPrincipal;
    }

    @Override
    public void registration(UserRequestDTO userRequestDTO) throws UsernameExistException, PasswordException {
        validateNewUsernameAndPassword(userRequestDTO);
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(encodePassword(userRequestDTO.getPassword()));
        if (userRepository.findAll().isEmpty()) {
            user.setRole(ROLE_ADMIN.name());
            user.setAuthorities(ROLE_ADMIN.getAuthorities());
        } else {
            user.setRole(ROLE_USER.name());
            user.setAuthorities(ROLE_USER.getAuthorities());
        }
        userRepository.save(user);
    }

    @Override
    public void registrationServer(AuthServerRequestDTO authServerRequestDTO) {
        Server server = new Server();
        server.setServerName(authServerRequestDTO.getServerName());
        server.setServerPassword(encodePassword(authServerRequestDTO.getServerPassword()));
        serverRepository.save(server);
    }

    @Override
    public Server findByServerName(String serverName) throws UsernameExistException {
        return serverRepository.findByServerName(serverName)
                .orElseThrow(() -> new UsernameExistException(USERNAME_ALREADY_EXISTS));
    }

    @Override
    public User findByUsername(String username) throws UsernameExistException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameExistException(USERNAME_ALREADY_EXISTS));
    }

    private void validateNewUsernameAndPassword(UserRequestDTO userRequestDTO) throws UsernameExistException, PasswordException {
        if (userRepository.findByUsername(userRequestDTO.getUsername()).isPresent()){
            throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
        }
        if (!userRequestDTO.getPassword().equals(userRequestDTO.getPassword2())){
            throw new PasswordException(PASSWORD_IS_NOT_VALID);
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
