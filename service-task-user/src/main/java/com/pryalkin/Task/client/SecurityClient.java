package com.pryalkin.Task.client;

import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.request.AuthorizationRequestDTO;
import com.pryalkin.Task.dto.request.TokenRequestDTO;
import com.pryalkin.Task.dto.response.AuthServerResponseDTO;
import com.pryalkin.Task.dto.response.AuthorizationResponseDTO;
import com.pryalkin.Task.dto.response.HttpResponse;
import com.pryalkin.Task.dto.response.UserResponseDTO;

public interface SecurityClient {

    AuthorizationResponseDTO authorization(AuthorizationRequestDTO authorizationRequestDTO);

    AuthServerResponseDTO authorizationServer(AuthServerRequestDTO authServerRequestDTO);
    AuthServerResponseDTO registrationServer(AuthServerRequestDTO authServerRequestDTO);
    UserResponseDTO getUserWithToken(TokenRequestDTO tokenRequestDTO);


    UserResponseDTO getUserWithId(Long executorId);
}
