package com.kuraiji.blog.services.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.UserDto;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.domain.entity.User;
import com.kuraiji.blog.domain.request.CreateUserRequest;
import com.kuraiji.blog.exception.DatabaseNotInitializedException;
import com.kuraiji.blog.repositories.RoleRepository;
import com.kuraiji.blog.repositories.UserRepository;
import com.kuraiji.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final Mapper<User, UserDto> userMapper;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(CreateUserRequest request) throws DatabaseNotInitializedException{
        Role userRole = roleRepository.findByName("user")
                .orElseThrow(() -> new DatabaseNotInitializedException("User role doesn't exist."));
        User user = User.builder()
                .handle(request.getHandle())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();
        return userMapper.mapTo(userRepository.save(user));
    }

    @Override
    public List<UserDto> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findOne(Long id) {
        return userRepository.findById(id).map(userMapper::mapTo);
    }

    @Override
    public boolean notExists(Long id) {
        return !userRepository.existsById(id);
    }

    @Override
    public UserDto fullUpdateUser(CreateUserRequest request, Long id) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setHandle(request.getHandle());
            existingUser.setEmail(request.getEmail());
            existingUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            return userMapper.mapTo(userRepository.save(existingUser));
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    @Override
    public UserDto partialUpdateUser(CreateUserRequest request, Long id) {
        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(request.getHandle()).ifPresent(existingUser::setHandle);
            Optional.ofNullable(request.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(request.getPassword()).ifPresent(
                    password -> existingUser.setPasswordHash(passwordEncoder.encode(password)));
            return userMapper.mapTo(userRepository.save(existingUser));
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
