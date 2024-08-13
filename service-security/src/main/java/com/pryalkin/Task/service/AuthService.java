package com.pryalkin.Task.service;

import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.request.UserRequestDTO;
import com.pryalkin.Task.exception.model.PasswordException;
import com.pryalkin.Task.exception.model.UsernameExistException;
import com.pryalkin.Task.model.Server;
import com.pryalkin.Task.model.User;

public interface AuthService {

    User findByUsername(String username) throws UsernameExistException;
    void registration(UserRequestDTO userRequestDTO) throws UsernameExistException, PasswordException;

    void registrationServer(AuthServerRequestDTO authServerRequestDTO);

    Server findByServerName(String serverName) throws UsernameExistException;
}
