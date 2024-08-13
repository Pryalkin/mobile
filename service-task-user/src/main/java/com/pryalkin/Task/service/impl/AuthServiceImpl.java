package com.pryalkin.Task.service.impl;

import com.pryalkin.Task.client.SecurityClient;
import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.response.AuthServerResponseDTO;
import com.pryalkin.Task.model.Server;
import com.pryalkin.Task.repository.ServerRepository;
import com.pryalkin.Task.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${app.name}")
    private String serverName;
    @Value("${app.password}")
    private String serverPassword;
    private final SecurityClient securityClient;
    private final ServerRepository serverRepository;


    @Override
    public String authorization(AuthServerRequestDTO authServerRequestDTO) {
        AuthServerResponseDTO authServerResponseDTO = securityClient.authorizationServer(authServerRequestDTO);
        return authServerResponseDTO.getToken();
//        // Путь к YAML-файлу
//        ResourceLoader resourceLoader = new DefaultResourceLoader();
//        Resource resource = resourceLoader.getResource("classpath:application.yml");
//        String yamlFilePath = null;
//        try {
//            yamlFilePath = resource.getFile().getPath();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        // Ключ, который нужно изменить
//        String keyToUpdate = "app.token";
//        // Новое значение для ключа
//        String newValue = tokenServer;
//        // Загружаем YAML-файл
//        Yaml yaml = new Yaml();
//        Map<String, Object> data = null;
//        try {
//            data = yaml.load(new FileReader(yamlFilePath));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        // Обновляем значение по ключу
//        String[] keyParts = keyToUpdate.split("\\.");
//        Map<String, Object> currentLevel = data;
//        for (int i = 0; i < keyParts.length - 1; i++) {
//            currentLevel = (Map<String, Object>) currentLevel.get(keyParts[i]);
//        }
//        currentLevel.put(keyParts[keyParts.length - 1], newValue);
//
//        // Сохраняем обновленный YAML-файл
//        DumperOptions options = new DumperOptions();
//        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
//        options.setPrettyFlow(true);
//        yaml = new Yaml(options);
//        try (FileWriter writer = new FileWriter(yamlFilePath)) {
//            yaml.dump(data, writer);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("YAML файл обновлен!");
//        return
    }

    @Override
    public void registration() {
        AuthServerRequestDTO authServerRequestDTO = new AuthServerRequestDTO();
        authServerRequestDTO.setServerName(serverName);
        authServerRequestDTO.setServerPassword(serverPassword);
        securityClient.registrationServer(authServerRequestDTO);
        System.out.println(authServerRequestDTO);
        String token = authorization(authServerRequestDTO);
        Server server = new Server();
        server.setServerName(serverName);
        server.setServerPassword(serverPassword);
        server.setToken(token);
        serverRepository.save(server);
    }

    @Override
    public String getToken() {
        return serverRepository.findByServerNameAndServerPassword(serverName, serverPassword)
                .orElseThrow().getToken();
    }

}
