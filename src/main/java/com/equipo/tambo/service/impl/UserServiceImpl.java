package com.equipo.tambo.service.impl;

import com.equipo.tambo.dto.UserRequest;
import com.equipo.tambo.dto.UserResponse;
import com.equipo.tambo.entity.Role;
import com.equipo.tambo.entity.User;
import com.equipo.tambo.repository.RoleRepository;
import com.equipo.tambo.repository.UserRepository;
import com.equipo.tambo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<UserResponse> listUsers() {
        return userRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse getById(Long id) {
        return toResponse(getUser(id));
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el usuario con ID " + id
                ));
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        Role role = roleRepository.findById(request.role())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el rol con ID " + request.role()
                ));

        User user = getUser(id);

        if (userRepository.existsByUserAndIdNot(request.user(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario '" + request.user() + "' ya existe.");
        }

        user.setName(request.name());
        user.setLastname(request.lastname());
        user.setUser(request.user());
        user.setPassword(request.password());
        user.setRole(role);

        return toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse changeUserStatus(Long id, boolean active) {
        User user = getUser(id);
        user.setActive(active);

        return toResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getUser(),
                user.getRole(),
                user.isActive()
        );
    }
}
