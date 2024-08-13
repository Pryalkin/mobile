package com.pryalkin.Task.client;

import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.request.AuthorizationRequestDTO;
import com.pryalkin.Task.dto.response.AuthServerResponseDTO;
import com.pryalkin.Task.dto.response.AuthorizationResponseDTO;
import com.pryalkin.Task.dto.response.HttpResponse;

public interface SecurityClient {

    AuthorizationResponseDTO authorization(AuthorizationRequestDTO authorizationRequestDTO);

    AuthServerResponseDTO authorizationServer(AuthServerRequestDTO authServerRequestDTO);
    AuthServerResponseDTO registrationServer(AuthServerRequestDTO authServerRequestDTO);
}
