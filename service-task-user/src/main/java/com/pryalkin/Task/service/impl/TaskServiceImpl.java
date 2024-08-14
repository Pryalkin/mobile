package com.pryalkin.Task.service.impl;

import com.pryalkin.Task.client.SecurityClient;
import com.pryalkin.Task.dto.request.TaskRequestDTO;
import com.pryalkin.Task.dto.request.TokenRequestDTO;
import com.pryalkin.Task.dto.response.UserResponseDTO;
import com.pryalkin.Task.enumeration.Priority;
import com.pryalkin.Task.enumeration.Status;
import com.pryalkin.Task.exception.model.TaskDontExistException;
import com.pryalkin.Task.model.Task;
import com.pryalkin.Task.repository.TaskRepository;
import com.pryalkin.Task.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.pryalkin.Task.constant.ExceptionConstant.TASK_DONT_ALREADY_EXISTS;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private SecurityClient securityClient;

    @Override
    public void createTask(TaskRequestDTO taskRequestDTO, String token) {
        TokenRequestDTO tokenRequestDTO = new TokenRequestDTO();
        tokenRequestDTO.setToken(token);
        System.out.println("SERVER TASK. METHOD CREATE TASK");
        UserResponseDTO userResponseDTO = securityClient.getUserWithToken(tokenRequestDTO);
        System.out.println("SERVER TASK. PRE DESTROY SECURITY");
        Task task = new Task();
        task.setHeading(taskRequestDTO.getHeading());
        task.setDescription(taskRequestDTO.getDescription());
        task.setStatus(Status.IN_WAITING.getStatus());
        Priority priority = Priority.valueOf(taskRequestDTO.getPriority().toUpperCase());
        task.setPriority(priority.getPriority());
        task.setCreatorId(userResponseDTO.getId());
        System.out.println("TASK: " + task);
        taskRepository.save(task);
    }

    @Override
    public void assign(Long taskId, Long executorId) throws TaskDontExistException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskDontExistException(TASK_DONT_ALREADY_EXISTS));
        UserResponseDTO userResponseDTO = securityClient.getUserWithId(executorId);
        task.setExecutorId(userResponseDTO.getId());
        taskRepository.save(task);
    }
}
