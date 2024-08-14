package com.pryalkin.Task.controller;

import com.pryalkin.Task.constant.HttpAnswer;
import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.request.AuthorizationRequestDTO;
import com.pryalkin.Task.dto.request.LoginUserRequestDTO;
import com.pryalkin.Task.dto.request.UserRequestDTO;
import com.pryalkin.Task.dto.response.AuthServerResponseDTO;
import com.pryalkin.Task.dto.response.AuthorizationResponseDTO;
import com.pryalkin.Task.dto.response.HttpResponse;
import com.pryalkin.Task.dto.response.UserResponseDTO;
import com.pryalkin.Task.exception.ExceptionHandling;
import com.pryalkin.Task.exception.model.EmailDontExistException;
import com.pryalkin.Task.exception.model.EmailValidException;
import com.pryalkin.Task.exception.model.PasswordException;
import com.pryalkin.Task.exception.model.EmailExistException;
import com.pryalkin.Task.model.Server;
import com.pryalkin.Task.model.ServerPrincipal;
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
    public ResponseEntity<HttpResponse> registration(@RequestBody UserRequestDTO userRequestDTO) throws EmailExistException, PasswordException, EmailValidException {
        authService.registration(userRequestDTO);
        return HttpAnswer.response(CREATED, USER_SUCCESSFULLY_REGISTERED);
    }

    @PostMapping("/registration/server")
    public ResponseEntity<HttpResponse> registrationServer(@RequestBody AuthServerRequestDTO authServerRequestDTO) throws EmailExistException, PasswordException {
        authService.registrationServer(authServerRequestDTO);
        return HttpAnswer.response(CREATED, USER_SUCCESSFULLY_REGISTERED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginUserRequestDTO userRequestDTO) throws EmailDontExistException, PasswordException {
        authService.validateCheckPassword(userRequestDTO);
        authenticate(userRequestDTO.getEmail(), userRequestDTO.getPassword());
        User loginUser = authService.findByEmail(userRequestDTO.getEmail());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(loginUser.getEmail());
        userResponseDTO.setName(loginUser.getName());
        userResponseDTO.setSurname(loginUser.getSurname());
        userResponseDTO.setRole(loginUser.getRole());
        userResponseDTO.setAuthorities(loginUser.getAuthorities());
        return new ResponseEntity<>(userResponseDTO, jwtHeader, OK);
    }

    @PostMapping("/authorization/user")
    public ResponseEntity<AuthorizationResponseDTO> authorization(@RequestBody AuthorizationRequestDTO authorizationRequestDTO) throws EmailExistException {
        AuthorizationResponseDTO responseDTO = new AuthorizationResponseDTO();
        responseDTO.setResult(true);
        return new ResponseEntity<>(responseDTO, OK);
    }


    @PostMapping("/authorization/server")
    public AuthServerResponseDTO authorizationServer(@RequestBody AuthServerRequestDTO authServerRequestDTO) throws EmailExistException {
        AuthServerResponseDTO responseDTO = new AuthServerResponseDTO();
        authenticate(authServerRequestDTO.getServerName(), authServerRequestDTO.getServerPassword());
        Server loginServer = authService.findByServerName(authServerRequestDTO.getServerName());
        responseDTO.setToken(jwtTokenProvider.generateJwtTokenForServer(new ServerPrincipal(loginServer)));
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
