package com.pryalkin.Task.service;

import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.request.AuthorizationRequestDTO;

public interface AuthService {

    void authorization(AuthServerRequestDTO authServerRequestDTO);
    void registration();

    String getToken();
}
