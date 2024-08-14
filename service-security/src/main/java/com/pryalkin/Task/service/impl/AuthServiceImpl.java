package com.pryalkin.Task.service.impl;

import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.request.LoginUserRequestDTO;
import com.pryalkin.Task.dto.request.TokenRequestDTO;
import com.pryalkin.Task.dto.request.UserRequestDTO;
import com.pryalkin.Task.dto.response.UserResponseDTO;
import com.pryalkin.Task.exception.model.*;
import com.pryalkin.Task.model.Server;
import com.pryalkin.Task.model.ServerPrincipal;
import com.pryalkin.Task.model.User;
import com.pryalkin.Task.model.UserPrincipal;
import com.pryalkin.Task.repository.ServerRepository;
import com.pryalkin.Task.repository.UserRepository;
import com.pryalkin.Task.service.AuthService;
import com.pryalkin.Task.utility.JWTTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

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
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.length() > "server".length() && username.substring(0, "server".length()).equals("server")){
            Server server = null;
            try {
                server = serverRepository.findByServerName(username)
                        .orElseThrow(() -> new EmailExistException(USERNAME_ALREADY_EXISTS));
            } catch (EmailExistException e) {
                throw new RuntimeException(e);
            }
            ServerPrincipal serverPrincipal = new ServerPrincipal(server);
            return serverPrincipal;
        }
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username));
        UserPrincipal userPrincipal = new UserPrincipal(user);
        log.info(FOUND_USER_BY_USERNAME + username);
        return userPrincipal;
    }

    @Override
    public void registration(UserRequestDTO userRequestDTO) throws EmailExistException, PasswordException, EmailValidException {
        validateEmail(userRequestDTO);
        validatePassword(userRequestDTO);
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(encodePassword(userRequestDTO.getPassword()));
        user.setName(userRequestDTO.getName());
        user.setSurname(userRequestDTO.getSurname());
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
    public Server findByServerName(String serverName) throws EmailExistException {
        return serverRepository.findByServerName(serverName)
                .orElseThrow(() -> new EmailExistException(USERNAME_ALREADY_EXISTS));
    }

    @Override
    public User findByEmail(String username) throws EmailDontExistException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new EmailDontExistException(EMAIL_DONT_ALREADY_EXISTS));
    }

    @Override
    public UserResponseDTO getUserWithToken(TokenRequestDTO tokenRequestDTO) throws EmailDontExistException {
        String email = jwtTokenProvider.getSubject(tokenRequestDTO.getToken());
        User user = findByEmail(email);
       return createUserResponseDTO(user);
    }

    private UserResponseDTO createUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setSurname(user.getSurname());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setAuthorities(user.getAuthorities());
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO getUserWithId(Long executorId) throws UserDontExistException {
        User user = userRepository.findById(executorId)
                .orElseThrow(() -> new UserDontExistException(USER_DONT_ALREADY_EXISTS));
        return createUserResponseDTO(user);
    }

    private void validateEmail(UserRequestDTO userRequestDTO) throws EmailExistException, EmailValidException {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()){
            throw new EmailExistException(EMAIL_ALREADY_EXISTS);
        }
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        if (VALID_EMAIL_ADDRESS_REGEX.matcher(userRequestDTO.getEmail()).matches()){
            throw new EmailValidException(EMAIL_IS_NOT_VALID);
        }
    }

    private void validatePassword(UserRequestDTO userRequestDTO) throws PasswordException {
        String regex = "^(?=.[a-z])(?=.[A-Z])(?=.[0-9])(?=.[\\w|\\W])";
        if (userRequestDTO.getPassword().matches(regex)) {
            throw new PasswordException(PASSWORD_IS_NOT_VALID);
        }
    }

    public void validateCheckPassword(LoginUserRequestDTO loginUserRequestDTO) throws PasswordException {
        if (!loginUserRequestDTO.getPassword().equals(loginUserRequestDTO.getPassword2())){
            throw new PasswordException(PASSWORD_IS_NOT_VALID);
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
