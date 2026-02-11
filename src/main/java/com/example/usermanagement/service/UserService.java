package com.example.usermanagement.service;
import com.example.usermanagement.dto.UserCreateRequest;
import com.example.usermanagement.dto.UserResponse;
import com.example.usermanagement.service.UserService;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> getAll();
    List<UserResponse> search(String keyword);
    UserResponse getById(UUID id);
    UserResponse create(UserCreateRequest request);
    UserResponse update(UUID id, UserCreateRequest request);
    void delete(UUID id);
    void toggleActive(UUID id);
}
