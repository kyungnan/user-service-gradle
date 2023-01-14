package com.example.userservicegradle.service;

import com.example.userservicegradle.dto.UserDto;
import com.example.userservicegradle.jpa.UserEntity;
import com.example.userservicegradle.jpa.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()));        // pwd 암호화

        // UserDto -> UserEntity
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);       // 딱 맞아 떨어질 경우만 매치
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        // DB에 insert
        userRepository.save(userEntity);

        // UserEntity -> UserDto
        return mapper.map(userEntity, UserDto.class);
    }
}
