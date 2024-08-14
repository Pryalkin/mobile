package com.pryalkin.Task.service;

import com.pryalkin.Task.dto.request.TaskRequestDTO;
import com.pryalkin.Task.exception.model.TaskDontExistException;

public interface TaskService {
    void createTask(TaskRequestDTO taskRequestDTO, String token);

    void assign(Long taskId, Long executorId) throws TaskDontExistException;
}
