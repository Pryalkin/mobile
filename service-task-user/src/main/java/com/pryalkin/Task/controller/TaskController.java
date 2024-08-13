package com.pryalkin.Task.controller;

import com.pryalkin.Task.dto.response.HttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {

    @PostMapping("/create")
    public void createTask(){
        System.out.println("Create Task");
    }

}
