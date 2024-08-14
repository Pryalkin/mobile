package com.pryalkin.Task.controller;

import com.pryalkin.Task.dto.request.TokenRequestDTO;
import com.pryalkin.Task.dto.response.UserResponseDTO;
import com.pryalkin.Task.exception.model.EmailDontExistException;
import com.pryalkin.Task.exception.model.UserDontExistException;
import com.pryalkin.Task.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {

    private final AuthService authService;

    @PostMapping("/user/token")
    public ResponseEntity<UserResponseDTO> getUserWithToken(@RequestBody TokenRequestDTO tokenRequestDTO) throws EmailDontExistException {
        return new ResponseEntity<>(authService.getUserWithToken(tokenRequestDTO), OK);
    }

    @PostMapping("/user/{executorId}")
    public ResponseEntity<UserResponseDTO> getUserWithId(@PathVariable Long executorId) throws UserDontExistException {
        return new ResponseEntity<>(authService.getUserWithId(executorId), OK);
    }


}
