package com.pryalkin.Task.controller;

import com.pryalkin.Task.constant.HttpAnswer;
import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.request.AuthorizationRequestDTO;
import com.pryalkin.Task.dto.request.UserRequestDTO;
import com.pryalkin.Task.dto.response.AuthServerResponseDTO;
import com.pryalkin.Task.dto.response.AuthorizationResponseDTO;
import com.pryalkin.Task.dto.response.HttpResponse;
import com.pryalkin.Task.dto.response.UserResponseDTO;
import com.pryalkin.Task.exception.ExceptionHandling;
import com.pryalkin.Task.exception.model.PasswordException;
import com.pryalkin.Task.exception.model.UsernameExistException;
import com.pryalkin.Task.model.Server;
import com.pryalkin.Task.model.User;
import com.pryalkin.Task.model.UserPrincipal;
import com.pryalkin.Task.service.AuthService;
import com.pryalkin.Task.utility.JWTTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pryalkin.Task.constant.HttpAnswer.*;
import static com.pryalkin.Task.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController extends ExceptionHandling {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/registration")
    public ResponseEntity<HttpResponse> registration(@RequestBody UserRequestDTO userRequestDTO) throws UsernameExistException, PasswordException {
        authService.registration(userRequestDTO);
        return HttpAnswer.response(CREATED, USER_SUCCESSFULLY_REGISTERED);
    }

    @PostMapping("/registration/server")
    public ResponseEntity<HttpResponse> registrationServer(@RequestBody AuthServerRequestDTO authServerRequestDTO) throws UsernameExistException, PasswordException {
        authService.registrationServer(authServerRequestDTO);
        return HttpAnswer.response(CREATED, USER_SUCCESSFULLY_REGISTERED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO) throws UsernameExistException {
        System.out.println("LOGIN USER");
        authenticate(userRequestDTO.getUsername(), userRequestDTO.getPassword());
        User loginUser = authService.findByUsername(userRequestDTO.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername(loginUser.getUsername());
        userResponseDTO.setRole(loginUser.getRole());
        userResponseDTO.setAuthorities(loginUser.getAuthorities());
        System.out.println("LOGIN USER22");
        return new ResponseEntity<>(userResponseDTO, jwtHeader, OK);
    }

    @PostMapping("/authorization")
    public ResponseEntity<AuthorizationResponseDTO> authorization(@RequestBody AuthorizationRequestDTO authorizationRequestDTO) throws UsernameExistException {
        AuthorizationResponseDTO responseDTO = new AuthorizationResponseDTO();
        responseDTO.setResult(true);
        return new ResponseEntity<>(responseDTO, OK);
    }


    @PostMapping("/authorization/server")
    public AuthServerResponseDTO authorizationServer(@RequestBody AuthServerRequestDTO authServerRequestDTO) throws UsernameExistException {
        System.out.println("/authorization/server");
        AuthServerResponseDTO responseDTO = new AuthServerResponseDTO();
        authenticate(authServerRequestDTO.getServerName(), authServerRequestDTO.getServerPassword());
        System.out.println("NEN NEN NEN");
        Server loginServer = authService.findByServerName(authServerRequestDTO.getServerName());
        System.out.println("NEN2 NEN2 NEN2");
        responseDTO.setToken(jwtTokenProvider.generateJwtTokenForServer(loginServer));
        System.out.println(responseDTO);
        return responseDTO;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }


}
