package com.pryalkin.Task.controller;

import com.pryalkin.Task.constant.HttpAnswer;
import com.pryalkin.Task.dto.request.TaskRequestDTO;
import com.pryalkin.Task.dto.response.HttpResponse;
import com.pryalkin.Task.exception.model.TaskDontExistException;
import com.pryalkin.Task.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

import static com.pryalkin.Task.constant.HttpAnswer.TASK_SUCCESSFULLY_CREATED;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController extends HeadlessException {

    private final TaskService taskService;
    public static final String TOKEN_PREFIX = "Bearer ";

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createTask(@RequestBody TaskRequestDTO taskRequestDTO,
                                                   HttpServletRequest httpServletRequest){
        String token = getUsernameWithToken(httpServletRequest);
        taskService.createTask(taskRequestDTO, token);
        return HttpAnswer.response(HttpStatus.CREATED, TASK_SUCCESSFULLY_CREATED);
    }

    @PostMapping("/assign/{taskId}/{executorId}")
    public ResponseEntity<HttpResponse> assign(@PathVariable Long taskId,
                                               @PathVariable Long executorId) throws TaskDontExistException {
        taskService.assign(taskId, executorId);
        return HttpAnswer.response(HttpStatus.OK, TASK_SUCCESSFULLY_CREATED);
    }

    private String getUsernameWithToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorizationHeader.substring(TOKEN_PREFIX.length());
    }



}
