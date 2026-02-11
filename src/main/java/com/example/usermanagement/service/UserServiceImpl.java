package com.example.usermanagement.service;
import com.example.usermanagement.dto.UserCreateRequest;
import com.example.usermanagement.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> search(String keyword) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim();
        if (normalizedKeyword.isEmpty()) {
            return getAll();
        }

        return userRepository.searchByKeyword(normalizedKeyword)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    @Override
    public UserResponse create(UserCreateRequest req) {
        User user = new User();
        user.setUsername(req.username);
        user.setPasswordHash("HASH_" + req.password); // mock
        user.setFullName(req.fullName);
        user.setEmail(req.email);
        user.setPhone(req.phone);
        user.setUserType(req.userType);
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());

        return toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse update(UUID id, UserCreateRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(req.fullName);
        user.setEmail(req.email);
        user.setPhone(req.phone);
        user.setUserType(req.userType);
        user.setUpdatedAt(LocalDateTime.now());

        return toResponse(userRepository.save(user));
    }

    @Override
    public void delete(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDeletedAt(LocalDateTime.now());
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    public void toggleActive(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
    }

    private UserResponse toResponse(User user) {
        UserResponse res = new UserResponse();
        res.id = user.getId();
        res.username = user.getUsername();
        res.fullName = user.getFullName();
        res.email = user.getEmail();
        res.phone = user.getPhone();
        res.userType = user.getUserType();
        res.isActive = user.getIsActive();
        return res;
    }
}
