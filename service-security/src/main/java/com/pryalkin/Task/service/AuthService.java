package com.pryalkin.Task.service;

import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.request.LoginUserRequestDTO;
import com.pryalkin.Task.dto.request.TokenRequestDTO;
import com.pryalkin.Task.dto.request.UserRequestDTO;
import com.pryalkin.Task.dto.response.UserResponseDTO;
import com.pryalkin.Task.exception.model.*;
import com.pryalkin.Task.model.Server;
import com.pryalkin.Task.model.User;

public interface AuthService {

    User findByEmail(String username) throws EmailDontExistException;
    void registration(UserRequestDTO userRequestDTO) throws EmailExistException, PasswordException, EmailValidException;

    void registrationServer(AuthServerRequestDTO authServerRequestDTO);

    Server findByServerName(String serverName) throws EmailExistException;

    void validateCheckPassword(LoginUserRequestDTO userRequestDTO) throws PasswordException;

    UserResponseDTO getUserWithToken(TokenRequestDTO tokenRequestDTO) throws EmailDontExistException;

    UserResponseDTO getUserWithId(Long executorId) throws UserDontExistException;
}
