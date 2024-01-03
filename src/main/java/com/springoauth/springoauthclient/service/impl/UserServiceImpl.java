package com.springoauth.springoauthclient.service.impl;

import com.springoauth.springoauthclient.exception.UserExistedException;
import com.springoauth.springoauthclient.exception.UserNotFoundException;
import com.springoauth.springoauthclient.model.dto.UserDTO;
import com.springoauth.springoauthclient.model.entity.UserEntity;
import com.springoauth.springoauthclient.model.mapper.UserMapper;
import com.springoauth.springoauthclient.repository.UserRepository;
import com.springoauth.springoauthclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<UserEntity> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO findByName(String username) {
        log.info("STARTING FINDING USER BY USERNAME");
        Optional<UserEntity> userEntity = userRepository.findByName(username);
        if (userEntity.isPresent()) {
            log.info("FOUND USER");
            UserDTO userDTO = UserMapper.fromEntityDto(userEntity.get());
            return userDTO;
        } else {
            throw new UserNotFoundException("User " + username + " not found");
        }
    }

    @Override
    public UserDTO createUser(UserEntity user) {
        log.info("STARTING FINDING USER BY USERNAME TO CREATING USER");
        Optional<UserEntity> existedUserEntity = userRepository.findByEmail(user.getEmail());

        if (existedUserEntity.isPresent()) {
            log.warn("USER IS EXISTED");
            throw new UserExistedException(user.getName());
        } else {
            log.info("CREATING NEW USER");

            try {
                UserDTO newUserDTO = UserMapper.fromEntityDto(userRepository.save(user));
                log.info("CREATED NEW USER SUCCESSFULLY");
                return newUserDTO;

            } catch (Exception exception) {
                log.error("CREATING NEW USER FAILED");
                throw exception;
            }

        }
    }
}
